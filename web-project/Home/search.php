<?php

if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);

	if($variable=='pyramids')
	{
		$que="SELECT * FROM `places` WHERE pname='Pyramids'";

		header("Location:../locations/location.html#Pyramids");


	}


}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='dahab')
	{
		$que="SELECT * FROM `places` WHERE pname='DAHAB'";

		header("Location:../locations/location.html#DAHAB");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='sinaa'||$variable=='sina')
	{
		$que="SELECT * FROM `places` WHERE pname='Sinaa'";

		header("Location:../locations/location.html#Sinaa");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='alexandria')
	{
		$que="SELECT * FROM `places` WHERE pname='Alexandria'";

		header("Location:../locations/location.html#Alexandria");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='noba')
	{
		$que="SELECT * FROM `places` WHERE pname='NOBA'";

		header("Location:../locations/location.html#NOBA");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='old egypt'||$variable=='egypt')
	{
		$que="SELECT * FROM `places` WHERE pname='Old Egypt'";

		header("Location:../locations/location.html#Old Egypt");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='luxor')
	{
		$que="SELECT * FROM `places` WHERE pname='Luxor'";

		header("Location:../locations/location.html#Luxor");


	}
}
if(isset($_POST['search_p'])) {
	$variable=strtolower($_POST['search_p']);
	if($variable=='siwaa'||$variable=='siwa')
	{
		$que="SELECT * FROM `places` WHERE pname='Siwaa'";

		header("Location:../locations/location.html#Siwaa");


	}
}
else
{
	header("Location:../locations/location.html");

}

?>

