// no use

let map;

function initMap() {
  // The location of USC
  var usc = { lat: 34.0224, lng: -118.2851 };
  // Request needed libraries.

  // The map, centered at usc
  map = new Map(document.getElementById("map"), {
    zoom: 8,
    center: usc,
    mapTypeId: "terrain",
  });

  new google.maps.Marker({
	  position: usc,
	  map: map,
	  title: "University of Southern California",
	  draggable: true,
	  animation: google.maps.animation.DROP,
  })
}
initMap();