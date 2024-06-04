
let allRestaurantsData = {};

document.addEventListener('DOMContentLoaded', function() {

    // Call the function to update the navbar
    // updateNavigationBar();	
	
    const searchButton = document.getElementById('searchButton');
    const searchResultsContainer = document.getElementById('searchResults');
    const homeImage = document.getElementById('homeImage');
    searchButton.addEventListener('click', function(event) {
        event.preventDefault(); // Prevent the form from submitting normally
		// Hide the home image
        homeImage.style.display = 'none';
        // Get the search parameters
        const term = document.getElementById('restaurantName').value;
        const latitude = document.getElementById('latitude').value;
        const longitude = document.getElementById('longitude').value;
        const sort = document.querySelector('input[name="sort"]:checked').value;

        if (!validateCoordinates(latitude, longitude)) {
            alert('Please enter valid latitude and longitude values.');
            return; // Exit the function if validation fails
        }
         var url = "ServletRestaurantSearch";
	    var params = new URLSearchParams(new FormData(document.getElementById('searchForm'))).toString();
	    var fullUrl = url + "?" + params;
	    console.log(fullUrl);
	    // “How to construc an ajax body?” prompt (4 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
        // Construct the request body with form data
        var requestBody = {
            term: term,
            latitude: latitude,
            longitude: longitude,
            sort_by: sort
        };
        fetch(fullUrl)
        .then(response => {
	      if (!response.ok) {
	        throw new Error('Network response was not ok');
	      }
	      return response.json(); 
	    })
        .then(data => {
            // Handle the response data from the server
            displaySearchResults(data);
        })
        .catch(error => {
            console.log(error);
        });
    });
});

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

document.getElementById('searchResults').addEventListener('click', function(event) {
    // Check if the clicked element is an image with the 'result-image' class
    if (event.target.classList.contains('result-image')) {
        const restaurantId = event.target.getAttribute('data-id');
        if (restaurantId && allRestaurantsData[restaurantId]) {
            displayRestaurantDetails(allRestaurantsData[restaurantId]);
        }
    }
});

function initMap() {
  // The location of USC
  var usc = { lat: 34.0224, lng: -118.2851 };
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

function displayRestaurantDetails(details) {
    const detailsContainer = document.getElementById('restaurantDetailsContainer');
    detailsContainer.innerHTML = ''; 

    const nameElement = document.createElement('h2');
    nameElement.textContent = details.name;

    const addressElement = document.createElement('p');
    addressElement.textContent = `Address: ${details.location.address1}`;

    const phoneElement = document.createElement('p');
    phoneElement.textContent = `Phone: ${details.phone}`;

    const cuisineElement = document.createElement('p');
    cuisineElement.textContent = `Cuisine: ${details.categories.map(category => category.title).join(', ')}`;

    const priceElement = document.createElement('p');
    priceElement.textContent = `Price: ${details.price}`;

    const ratingElement = document.createElement('p');
    ratingElement.textContent = `Rating: ${'★'.repeat(Math.round(details.rating))}`;

    const favButton = document.createElement('button');
    favButton.textContent = details.isFavorite ? 'Remove from Favorites' : 'Add to Favorites';
    favButton.onclick = function() {
        const action = details.isFavorite ? 'remove' : 'add';
        toggleFavorite(details, action, favButton);
    };


    const addReservationButton = document.createElement('button');
    addReservationButton.textContent = 'Add Reservation';
    addReservationButton.onclick = function() {
        // Implement functionality to add a reservation
        // This will involve making another AJAX call to your server
    };

    // Append the detailed info elements to the detailsContainer
    detailsContainer.appendChild(nameElement);
    detailsContainer.appendChild(addressElement);
    detailsContainer.appendChild(phoneElement);
    detailsContainer.appendChild(cuisineElement);
    detailsContainer.appendChild(priceElement);
    detailsContainer.appendChild(ratingElement);
    // Append the button to the details container
    detailsContainer.appendChild(favButton);
    detailsContainer.appendChild(addReservationButton);

    // After populating the detailsContainer with the correct info, make it visible
    detailsContainer.style.display = 'block';
	// hide the search results while showing the details
    document.getElementById('searchResults').style.display = 'none';
}

// ajax call for fav
function toggleFavorite(details, action, button) {
    fetch('/ServletFavorites', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ action: action, restaurant: details })
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            details.isFavorite = (action === 'add');
            button.textContent = details.isFavorite ? 'Remove from Favorites' : 'Add to Favorites';
            alert(data.message); // Or update the UI in another way
        } else {
            alert('Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred.');
    });
}

function validateCoordinates(lat, lng) {
    var latRegex = /^-?([1-8]?[1-9]|[1-9]0)\.{1}\d{1,6}$/;
    var lngRegex = /^-?(([-+]?)([\d]{1,3})((\.)(\d+))?)$/;
    return latRegex.test(lat) && lngRegex.test(lng);
}

function displaySearchResults(jsonData) {
    const data = typeof jsonData === 'string' ? JSON.parse(jsonData) : jsonData;
    const searchResultsContainer = document.getElementById('searchResults');
    searchResultsContainer.innerHTML = ''; // Clear previous results

    if(data && data.businesses) {
        data.businesses.forEach(business => {
            const resultDiv = document.createElement('div');
            resultDiv.classList.add('search-result');

            const imageElement = new Image();
            imageElement.src = business.image_url;
            imageElement.alt = business.name;
            imageElement.classList.add('result-image');

            const detailsDiv = document.createElement('div');
            detailsDiv.classList.add('result-details');

            const nameElement = document.createElement('h2');
            nameElement.textContent = business.name;

            const addressElement = document.createElement('p');
            addressElement.textContent = business.location.address1;

            const phoneElement = document.createElement('p');
            phoneElement.textContent = business.phone;

            const ratingElement = document.createElement('p');
            ratingElement.textContent = `Rating: ${'★'.repeat(Math.round(business.rating))}`;

            const yelpLink = document.createElement('a');
            yelpLink.href = business.url;
            yelpLink.textContent = 'View on Yelp';
            yelpLink.target = '_blank';

            detailsDiv.appendChild(nameElement);
            detailsDiv.appendChild(addressElement);
            detailsDiv.appendChild(phoneElement);
            detailsDiv.appendChild(ratingElement);
            detailsDiv.appendChild(yelpLink);

            resultDiv.appendChild(imageElement);
            resultDiv.appendChild(detailsDiv);

            searchResultsContainer.appendChild(resultDiv);
        });
    } else {
        searchResultsContainer.innerHTML = '<p>No results found.</p>';
    }
    searchResultsContainer.style.display = 'block';
}

// Function to update navigation bar based on login status
    function updateNavigationBar() {
        fetch('/ServletRestaurantSearch', { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            const navbar = document.getElementById('navbar');
            navbar.innerHTML = data.isLoggedIn
                ? '<ul><li><a href="home.html">Home</a></li><li><a href="favorites.html">Favorites</a></li><li><a href="reservations.html">Reservations</a></li><li><a href="logout.html">Logout</a></li></ul>'
                : '<ul><li><a href="home.html">Home</a></li><li><a href="login.html">Login / Sign Up</a></li></ul>';
        })
        .catch(error => {
			console.log('error');
		});
    }
