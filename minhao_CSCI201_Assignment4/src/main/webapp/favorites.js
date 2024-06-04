/**
 * 
 */
var count = 0;
document.getElementById('googleMapsButton').addEventListener('click', function() {	
	if (count % 2 == 0) {
		document.getElementById('map').style.display = 'block';
    	initMap();
	}
	else {	
		document.getElementById('map').style.display = 'none';
	}	
   	count++;
});
function initMap() {
  // The location of USC
  var usc = { lat: 34.0224, lng: -118.2851 };
  // Request needed libraries.

  // The map, centered at usc
  var map = new google.maps.Map(document.getElementById("map"), {
    zoom: 8,
    center: usc,
    mapTypeId: "terrain",
  });

  new google.maps.Marker({
	  position: usc,
	  map: map,
	  title: "University of Southern California",
	  draggable: true,
  })
}