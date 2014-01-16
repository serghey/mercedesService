<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CroAuto Mercedes Service</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/justified-nav.css" rel="stylesheet">
</head>
<style>

</style>
<body>
 <div class="masthead container">
 	<h3 class="text-muted"><img alt="" src="img/logo.png"></h3>
	  <ul class="nav nav-justified">
	  	  <li style="text-align: center">
					<a href="main"><span class="glyphicon glyphicon-home"></span></a>
		  </li>
          <li class="active"><a href="registration">Inregistrare Comanda</a></li>
          <li><a href="factura">Eliberare Factura</a></li>
          <li><a href="history">Istoric Lucrari</a></li>
          <li><a href="exit">Iesire</a></li>
        </ul>
 </div>
 <div class="container" style="margin-top: 40px;">
			
				<div class="row">
					<div class="form-group">
						<div class="input-group col-lg-4 col-lg-offset-2">
							<input type="text" class="form-control" placeholder="Nume/Prenume Client" name="client">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md" type="button"><span class="glyphicon glyphicon-search"></span>  Cauta</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<div class="input-group col-lg-4 col-lg-offset-2">
							<input type="text" class="form-control" placeholder="Model Auto" name="model">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md dropdown-toggle pull-left" data-toggle="dropdown" type="button"> Marca  <span class="caret"></span></button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<div class="input-group col-lg-4 col-lg-offset-2">
							<input type="text" class="form-control" placeholder="Anul Fabricarii" name="year">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md dropdown-toggle pull-left" type="button"> Anul&nbsp&nbsp&nbsp <span class="caret"></span></button>
						</div>
					</div>
				</div>
				<div class="row">
					<br/>
				</div>
				<div class="row">
					
				</div>
</div>
<div class="container">
	<table class="table table-striped">
		<thead>
			<tr>
				<th>#</th>
				<th>Denumirea Piesei</th>
				<th>Pretul Piesei</th>
				<th>Executor Lucru</th>
				<th>PretulLucrului</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					1
				</td>
				<td>			
					<div class="form-group">
						<div class="input-group">		
							<input type="text" class="form-control" placeholder="Denumirea Piesei" name="partName">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md" type="button"><span class="glyphicon glyphicon-search"></span>  Cauta</button>
						</div>
					</div>	
				</td>
				<td>
					<div class="form-group">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Pretul Piesei" name="partPrice">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md" type="button"><span class="glyphicon glyphicon-search"></span>  Cauta</button>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<div class="input-group">		
							<input type="text" class="form-control" placeholder="Executor Lucru" name="worker">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md" type="button"><span class="glyphicon glyphicon-search"></span>  Cauta</button>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Pretul Lucrului" name="workPrice">
							<span class="input-group-btn">
							<button class="btn btn-default btn-md" type="button"><span class="glyphicon glyphicon-search"></span>  Cauta</button>
						</div>
					</div>
				</td>
				<td>
					<div class="form-group">
						<a href="#" class="btn btn-primary pull-right">  Adauga</a>
					</div>
				</td>
			</tr>
		</tbody>	
	</table>
</div>
    <script src="https://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>
</html>