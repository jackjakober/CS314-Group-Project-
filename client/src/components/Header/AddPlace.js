import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	Collapse,
	ModalFooter,
} from 'reactstrap';
import Coordinates from 'coordinate-parser';
import { reverseGeocode } from '../../utils/reverseGeocode';
import { getOriginalServerUrl, sendAPIRequest } from '../../utils/restfulAPI.js';


export default function AddPlace(props) {
	const [foundPlace, setFoundPlace] = useState();
	const [coordString, setCoordString] = useState('');
	const [Place_Flag, setPlace_Flag] = useState(false);
	const [Found_Places, setFound_Places] = useState([]);
	const [selectedPlaces, setSelectedPlaces] = useState([]);
	const [selectedCount, setSelectedCount] = useState(0);
	
	return (
		<Modal isOpen={props.isOpen} toggle={props.toggleAddPlace} style={{maxWidth: '800px'}} unmountOnClose={false}>
			<AddPlaceHeader toggleAddPlace={props.toggleAddPlace} />
			<PlaceSearch
				foundPlace={foundPlace}
				setFoundPlace={setFoundPlace}

				coordString={coordString}
				setCoordString={setCoordString}

				Place_Flag={Place_Flag}
				setPlace_Flag={setPlace_Flag}
				
				Found_Places={Found_Places}
				setFound_Places={setFound_Places}
				
				selectedPlaces={selectedPlaces}
				setSelectedPlaces={setSelectedPlaces}

				selectedCount={selectedCount}
				setSelectedCount={setSelectedCount}
			/>
			<AddPlaceFooter
				append={props.append}
				appendBunch={props.appendBunch}
				foundPlace={foundPlace}
				setCoordString={setCoordString}
				Place_Flag={Place_Flag}
				selectedPlaces={selectedPlaces}
				setSelectedPlaces={setSelectedPlaces}
				selectedCount={selectedCount}
				setSelectedCount={setSelectedCount}
			/>
		</Modal>
	);
}

function AddPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleAddPlace}>
			Add a Place
		</ModalHeader>
	);
}

function PlaceSearch(props) {
	useEffect(() => {
		verifyCoordinates(props.coordString, props.setFoundPlace, props.setPlace_Flag, props.setFound_Places);
	}, [props.coordString]);
	if(!props.Place_Flag) {

	}
	return (
		<ModalBody>
			<Col>
				<Input
					onChange={(input) => props.setCoordString(input.target.value)}
					placeholder='ENTER Place Name OR Latitude, Longitude'
					data-testid='coord-input'
					value={props.coordString}
				/>
				{(!props.Place_Flag) ? <PlaceInfo foundPlace={props.foundPlace} /> :
              			<ShowPlaces 
						Found_Places={props.Found_Places} 
						setSelectedPlaces={props.setSelectedPlaces}
						selectedPlaces={props.selectedPlaces}
						setSelectedCount={props.setSelectedCount}
						selectedCount={props.selectedCount} />} 
			</Col>
		</ModalBody>
	);
	
}

function ShowPlaces(props) {
    return(
        <table>
			<tbody>
            {props.Found_Places.map((place, index) => (
				<ModalBody>
					 <Col>
						 <input 
						 	type="checkbox" 
						 	class="largerCheckbox" 
						 	id="addPlaceBox" 
							data-testid={"addPlaceBox-" + index}
						 	style={{width: '20px', height: '20px'}}
						 	onClick={() => {
								processPlaceSelect(props, place);
								props.setSelectedPlaces(props.selectedPlaces);
							}}>

						</input>
						 {'     '}{place.name}{', '}{place.municipality}{', '}{place.latitude}{', '}{place.longitude}
					</Col>
				</ModalBody>
            ))}
        </tbody>
		</table>
    );
}

function processPlaceSelect(props, place)
{
	let selectedArray = {selectedPlaces: props.selectedPlaces, setSelectedPlaces: props.setSelectedPlaces};
	if(!props.selectedPlaces.includes(place))
	{
		addPlace(selectedArray, place);
		props.setSelectedCount(props.selectedCount + 1);
	}
	else
	{
		removePlaceAt(selectedArray, place);
		props.setSelectedCount(props.selectedCount - 1);
	}
}

function addPlace(selectedArray, place)
{
	const [selectedPlaces,setSelectedPlaces] = [selectedArray.selectedPlaces, selectedArray.setSelectedPlaces];
	let newArray = selectedPlaces;
	newArray.push(place);
	setSelectedPlaces(newArray);
}

function removePlaceAt(selectedArray, place)
{
	const [selectedPlaces,setSelectedPlaces] = [selectedArray.selectedPlaces, selectedArray.setSelectedPlaces];
	const index = selectedPlaces.indexOf(place);
	let newArray2 = selectedPlaces.splice(index,1);
	setSelectedPlaces(newArray2);
}

function PlaceInfo(props) {
	return (
		<Collapse isOpen={!!props.foundPlace}>
			<br />
			{props.foundPlace?.formatPlace()}
		</Collapse>
	);
}

function AddPlaceFooter(props) {

	let label = "Add Place";
	if(props.Place_Flag)
	{
		label = "Add Selected Places";
	}

	return (
		<ModalFooter>
			<Button
				color='primary'
				onClick={() => {
					if(props.foundPlace) {addSinglePlace(props);} 
					else {addPlaces(props);}
					eraseSelected(props);
				}}
				data-testid='add-place-button'
				disabled={noPlacesSelected(props.selectedCount) && !props.foundPlace}
			>
				{label}
			</Button>
		</ModalFooter>
	);
}

async function addPlaces(props)
{
	props.appendBunch(props.selectedPlaces);
	props.setCoordString('');
	props.setSelectedPlaces([]);
}

function addSinglePlace(props)
{
	props.append(props.foundPlace);
	props.setCoordString('');
}

function eraseSelected(props)
{
	props.setSelectedPlaces([]);
	props.setSelectedCount(0);
}

function noPlacesSelected(selectedCount)
{
	return selectedCount == 0;
}

async function verifyCoordinates(coordString, setFoundPlace, setPlace_Flag, setFound_Places) {
	try {
		const latLngPlace = new Coordinates(coordString);
		const lat = latLngPlace.getLatitude();
		const lng = latLngPlace.getLongitude();
		if (isLatLngValid(lat,lng)) {
			const fullPlace = await reverseGeocode({ lat, lng });
			setFoundPlace(fullPlace);
			setPlace_Flag(false);
		}
	} catch (error) {
		setFoundPlace(undefined);

		if(coordString.length >= 3)
		{
			searchDatabase({coordString: coordString, setFound_Places: setFound_Places});
			setPlace_Flag(true);
		}
		else
		{
			setPlace_Flag(false);
		}
	}
}

async function searchDatabase(props)
{
	try
	{
		const serverUrl= getOriginalServerUrl();

		const FindResponse = await sendAPIRequest({requestType: 'find', match: props.coordString , limit: 10}, serverUrl);

		if(FindResponse)
		{
			props.setFound_Places(FindResponse.places);
		}else{
			props.setFound_Places("The Nightman Cometh");
		}


	}catch{}
	
	
}

function isLatLngValid(lat,lng) {
	return (lat !== undefined && lng !== undefined);
}