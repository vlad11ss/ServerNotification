<?

//http://mavito.test1.ru/test/ssl/android_send.php
define('sr_code', true);
include("../../inc/const.php");

if (strpos($_SERVER["HTTP_HOST"],".test1.ru")>0) 
{
	$server_ip="127.0.0.1";
}
else 
{
	$server_ip="46.17.43.243";
}
print "<br>".date("H:i:s");

$url = "http://test2.ru/test.html";

$ch = curl_init($url);
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);
//curl_setopt($ch, CURLOPT_PROXY, "localhost:3129");	
print "<br>server_ip= $server_ip<br>";


if (!$direct) {curl_setopt($ch, CURLOPT_PROXY, "$server_ip:3130");				}

//для авито не работает с мобильной версией и соответственно с мобильными юзерагентами
//странно, но заработало. не понял в чем была проблема 29/05/22

// $headers = array('Title: titletest',
// 'Body: bodytest',	
// 'Token: dOWWzpckQh6zcr1y5E4jid:APA91bGGFYCSJkThO-IZaf9QZW8o8VP4sSzKjTJzd94Kr47wTmy0cs4SXWDBjdQ7HrtdjZQYY5X8s7KdVq60Hp4xKRDZ_vlgeUDfjvBczgr3dY1YsoXmeax2uUpWw2ShxYcT3ZEqGyk3'
// ); 

$headers = array('Title: titletest',
'Body: bodytest',	
'Token: f8qQFdBJTBG7hYxZueBOIZ:APA91bFTOREHvljYtMEz6nH6xu52Mu0c0wv91tBEXhST8_xRMNnnL2Hmej4UeuvSmoei54eVKyFE7mhrS7ZkNoQWBb28HkHyOCdU1EARjnP5iXiqxLqY-IOMnsKmGheXphklpipkuvE2'
); 
//если в Proxystr задать none - тогда без прокси

print_r($headers);
print "<hr>\n";

curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
//curl_setopt($ch, CURLOPT_HEADER, true);
curl_setopt($ch,CURLOPT_TIMEOUT,5);
curl_setopt($ch, CURLOPT_ENCODING, "");
$html = curl_exec($ch);
print "<br>получено ".strlen($html)."<br>";

if (strlen($html)<15000) 
{print $html;}
else 
{
	
	print "<textarea>$html</textarea>";
}
print "<br>".date("H:i:s");
?>