package md.mercedes.service.backend;

import md.mercedes.service.util.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.exceptions.BadRequestException;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.query.ColumnFamilyQuery;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class CassandraConnection {

	private final ColumnFamily<String, String> PRODUCT_STORE;
	private static Keyspace keyspace;
	final static Logger log = LoggerFactory.getLogger(CassandraConnection.class);
	/**
	 * Constructor 
	 * the parameters are passed through cassandra.properties file
	 * It builds the necessary keyspace and column families
	 */
	public CassandraConnection() {

		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
				.forCluster(AppConfig.getInstance().getProperty("cassandra.cluster.name"))
				.forKeyspace(AppConfig.getInstance().getProperty("cassandra.keyspace.name"))
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl().setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE))
				.withConnectionPoolConfiguration(
						new ConnectionPoolConfigurationImpl(AppConfig.getInstance().getProperty("cassandra.connection.poolName"))
								.setPort(Integer.valueOf(AppConfig.getInstance().getProperty("cassandra.connection.port")))
								.setMaxConnsPerHost(
										Integer.valueOf(AppConfig.getInstance().getProperty("cassandra.connection.maxConnections")))
								.setSeeds(AppConfig.getInstance().getProperty("cassandra.cluster.hosts")))
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl().setCqlVersion(
								AppConfig.getInstance().getProperty("cassandra.cqlVersion"))
								.setTargetCassandraVersion(
										AppConfig.getInstance().getProperty("cassandra.version")))
				.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
		keyspace = context.getClient();

		PRODUCT_STORE = ColumnFamily.newColumnFamily(AppConfig.getInstance().getProperty("cassandra.cf.product"), StringSerializer.get(), StringSerializer.get());

		initCassandraCF(keyspace);
	}

	/**
	 * Same as {@link #CassandraBackend()} but the parameters are passed through
	 * constructor
	 * 
	 * @param clusterName
	 * @param keyspaceName
	 * @param cassandraSeeds
	 * @param poolConfiguration
	 * @param cassandraPort
	 * @param maxConnsPerHost
	 * @param cassandraVersion
	 * @param cassandraCQLVersion
	 */
	public CassandraConnection(String clusterName, String keyspaceName, String cassandraSeeds, String poolConfiguration,
			int cassandraPort, int maxConnsPerHost, String cassandraVersion, String cassandraCQLVersion) {
		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
				.forCluster(clusterName)
				.forKeyspace(keyspaceName)
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl().setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE))
				.withConnectionPoolConfiguration(
						new ConnectionPoolConfigurationImpl(poolConfiguration).setPort(cassandraPort)
								.setMaxConnsPerHost(maxConnsPerHost).setSeeds(cassandraSeeds))
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl().setCqlVersion(cassandraCQLVersion).setTargetCassandraVersion(
								cassandraVersion)).withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
		keyspace = context.getClient();

		PRODUCT_STORE = ColumnFamily.newColumnFamily(AppConfig.getInstance().getProperty("cassandra.cf.log"), StringSerializer.get(), StringSerializer.get());

		initCassandraCF(keyspace);
	}

	/**
	 * @return MutationBatch
	 */
	public MutationBatch prepareMutationBatch() {
		return keyspace.prepareMutationBatch();
	}

	/**
	 * @param log_cf - created column family
	 * @return the query
	 */
	public ColumnFamilyQuery<String, String> prepareQuery(ColumnFamily<String, String> log_cf) {
		return keyspace.prepareQuery(log_cf);
	}

	/**
	 * @return log column family
	 */
	public ColumnFamily<String, String> getPRODUCT_CF() {
		return PRODUCT_STORE;
	}

	/**
	 * @return the keyspace
	 */
	public Keyspace getKeyspace() {
		return keyspace;
	}

	/**
	 * Same as {@link #loadCassandraStructure()}
	 * 
	 * @param keyspace
	 */
	private void initCassandraCF(Keyspace keyspace) {
		try {
			keyspace.describeKeyspace();
		} catch (BadRequestException e) {
			try {
				loadCassandraStructure();
				log.info("Database structure was loaded successfully");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that creates necessary keyspace & column families in cassandra
	 * database
	 * 
	 * @throws Exception
	 */
	public void loadCassandraStructure() throws Exception {
		if (AppConfig.getInstance().getProperty("cassandra.keyspace.replicationStrategy")
				.equals("SimpleStrategy")) {
			keyspace.createKeyspace(ImmutableMap
					.<String, Object> builder()
					.put("strategy_options",
							ImmutableMap.<String, Object> builder().put("replication_factor", "1").build())
					.put("strategy_class", "SimpleStrategy").build());
		} else if (AppConfig.getInstance().getProperty("cassandra.keyspace.replicationStrategy")
				.equals("NetworkTopologyStrategy")) {
			keyspace.createKeyspace(ImmutableMap
					.<String, Object> builder()
					.put("strategy_options",
							ImmutableMap.<String, Object> builder().put("us-east", "3").put("eu-west", "3").build())
					.put("strategy_class", "NetworkTopologyStrategy").build());
		} else
			throw new Exception("Unsupported Strategy Type, please check properties configurations");

		keyspace.createColumnFamily(PRODUCT_STORE, null);
	}

	/**
	 * Method that removes existing column families
	 * 
	 * @throws ConnectionException
	 */
	public void dropColumnFamilies() throws ConnectionException {
		keyspace.dropColumnFamily(PRODUCT_STORE);
	}

	/**
	 * Method that removes existing keyspace
	 * 
	 * @throws ConnectionException
	 */
	public void dropKeyspace() throws ConnectionException {
		keyspace.dropKeyspace();
	}

}
