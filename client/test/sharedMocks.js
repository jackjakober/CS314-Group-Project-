import { Place } from "../src/models/place.model";
export const VALID_CONFIG_RESPONSE = JSON.stringify({
    requestType: 'config',
    serverName: 't99',
    features: ['config']
});

export const VALID_FIND_RESPONSE = JSON.stringify({
    "match": "0CO1",
    "limit": 10,
    "found": 1,
    "places": [
        {
            "continent": "NA",
            "country": "United States",
            "altitude": "5170",
            "latitude": "40.0332984924",
            "name": "Dave's Airport",
            "municipality": "Louisville",
            "index": "1",
            "id": "0CO1",
            "region": "Colorado",
            "longitude": "-105.124000549"
        }
    ],
    "requestType": "find"
});

export const MOCK_PLACES_FOR_TOUR_1 = [{name:"place1", latitude: 32.36, longitude: -102.42, notes:"Home of CSU"},
{name:"place2", latitude: 58.53, longitude: -92.58, notes:"The big city"},
{name:"place3", latitude: 32.36, longitude: -98.20, notes:"Home of CU"},
{name:"place4", latitude: 57.88, longitude: -97.32, notes:"Home of CU"}];

export const MOCK_TOUR_RESPONSE_1 = JSON.stringify({
    "earthRadius": 3959.0,
    "response": 1.0,
    "places": [MOCK_PLACES_FOR_TOUR_1[0], MOCK_PLACES_FOR_TOUR_1[4], MOCK_PLACES_FOR_TOUR_1[2], MOCK_PLACES_FOR_TOUR_1[3]],
    "requestType": "tour"
});

export const DAVE_MOCK_PLACE = {
        "continent": "NA",
        "country": "United States",
        "altitude": "5170",
        "latitude": "40.0332984924",
        "name": "Dave's Airport",
        "municipality": "Louisville",
        "index": "1",
        "id": "0CO1",
        "region": "Colorado",
        "longitude": "-105.124000549"
    };

export const VALID_DAVE_FIND_RESPONSE = JSON.stringify({
    "match": "dave",
    "limit": 3,
    "found": 3,
    "places": [DAVE_MOCK_PLACE,DAVE_MOCK_PLACE,DAVE_MOCK_PLACE],
    "requestType": "find"
});


export const INVALID_REQUEST = JSON.stringify({
    invalid: 'this is an invalid response to fail the schema'
});

export const MOCK_PLACES = [
    new Place({ name: 'Place A', latitude: "40.0", longitude: "-20.0" }),
    new Place({ name: 'Place B', latitude: "-20.0", longitude: "50.0" }),
    new Place({ name: '123 Test', city: 'expanded test', latitude: "50.0", longitude: "60.0"}),
    new Place({lat: '27.0', lng: '100.0', road: 'Main St'}),
    new Place({lat: '80', lng: '-80', suburb: 'Test Suburb', name: 'Test Place'}),
    new Place({house_number: '123', road: 'Main St', suburb: 'Test Suburb', lat: '5.0', lng: '-40.0'}),
    new Place({latitude: "0.0", longitude: "0.0", postcode: '12345'}),
    new Place({lat: '50', lng: '50', road: 'Main St', country: 'Test Country'})
];

export const REVERSE_GEOCODE_RESPONSE = JSON.stringify({
    "place_id": 259127396,
    "licence": "Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
    "osm_type": "relation",
    "osm_id": 8539568,
    "lat": "40.57066025",
    "lng": "-105.08539645568865",
    "place_rank": 30,
    "category": "amenity",
    "type": "university",
    "importance": 0.4948531325947546,
    "addresstype": "amenity",
    "name": "Colorado State University",
    "display_name": "Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States",
    "address": {
        "amenity": "Colorado State University",
        "road": "South College Avenue",
        "city": "Fort Collins",
        "county": "Larimer County",
        "state": "Colorado",
        "postcode": "80525-1725",
        "country": "United States",
        "country_code": "us"
    },
    "boundingbox": [
        "40.5527786",
        "40.5789122",
        "-105.0972937",
        "-105.0721817"
    ]
});

export const MOCK_PLACE_RESPONSE = {
    country: "United States",
    defaultDisplayName: "Colorado State University",
    latitude: '40.57',
    longitude: '-105.085',
    name: 'Colorado State University',
    municipality: 'Fort Collins',
    postcode: "80525-1725",
    region: "Colorado",
    streetAddress: "South College Avenue",
};