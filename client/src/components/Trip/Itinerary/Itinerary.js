import React, { useEffect, useState } from 'react';
import { useToggle } from '../../../hooks/useToggle';
import { Table, Collapse } from 'reactstrap';
import { latLngToText, placeToLatLng } from '../../../utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';
import { getOriginalServerUrl, sendAPIRequest } from '../../../utils/restfulAPI.js';
import { LOG } from '../../../utils/constants';
import styles from '../../../static/styles/student-styles.scss';

export default function Itinerary(props) {

	const [distances, setDistances] = useState([0]);
	const [totalDistance, setTotalDistance] = useState(0);

	

	return (
		<Table responsive>
			<TripHeader
				tripName={props.tripName}
				distances={distances}
				totalDistance={totalDistance}
				setTotalDistance={setTotalDistance}
				units={"Miles"}
				
			/>
			<PlaceList
				data-testid={`ThePlaceList123`}
				places={props.places}
				earthRadius={props.earthRadius}
				placeActions={props.placeActions}
				selectedIndex={props.selectedIndex}
				distances={distances}
				setDistances={setDistances}
			/>
		</Table>
	);
}




function TripHeader(props) {
	getTotalDistance(props);
	return (
		<thead>
			<tr>
				<th
					className='trip-title'
					data-testid='trip-place-number'
					style={{textAlign:'left', fontSize:'18px', width:'15%'}}
					>
					{props.tripName}
				</th>
				<th></th><th></th><th></th><th
						class="html"
						style={{textAlign: 'center', fontSize: '16px', width:'%'}}
						>
						Units: Miles
				</th>
			</tr>
			<tr>
				<th
					className='trip-place-number'
					data-testid='trip-place-number'
					style={{textAlign:'left', fontSize:'16px', width:'15%'}}
				>
					#
				</th>
				<th
					className='trip-header-title'
					data-testid='trip-header-title'
					style={{textAlign:'left', fontSize:'16px', width:'38%'}}
				>
					location
				</th>

				<th className='trip-header-cumulative'
					data-testid='trip-header-cumulative'
					style={{textAlign:'center', fontSize:'16px',width:'20%'}}
				>
					{" "}{props.units}
				</th>

				<th className='trip-header-leg'
					style={{textAlign:'center', fontSize:'16px',width:'39%'}}
					data-testid='trip-header-leg'>
					{"Leg Length"}
				</th>
				<th className='trip-header-leg'
					style={{textAlign:'center', fontSize:'16px',width:'%'}}
					data-testid='trip-header-leg'>
				{"Total: "}{props.totalDistance}

			    </th>
			</tr>
		</thead>
	);
}

function PlaceList(props) {
	
	return (	
		<tbody>
			{props.places.map((place, index) => (
				<PlaceRow
					key={`table-${JSON.stringify(place)}-${index}`}
					place={place}
					placeActions={props.placeActions}
					selectedIndex={props.selectedIndex}
					index={index}
					places={props.places}
					earthRadius={props.earthRadius}
					setDistances={props.setDistances}
					distances={props.distances}
				/>
			))}
		</tbody>
	);
}

function PlaceRow(props) {

	if(needToRecalculateDistances({distancesLength: props.distances.length, placesLength: props.places.length}))
	{
		try {
			getDistances(props); 
		} 
		catch {}
	}

	const [showFullName, toggleShowFullName] = useToggle(false);
	const name = props.place.defaultDisplayName;
	const location = latLngToText(placeToLatLng(props.place));
	return (
		<tr className={props.selectedIndex === props.index ? 'selected-row' : ''}>
			<td
			style={{width:'3%', fontSize:'18px'}}
			>
				<strong>{props.index + 1}.</strong>
			</td>
			<td 
				style={{width:'36%', fontSize:'18px'}}
				data-testid={`place-row-${props.index}`}
				onClick={() =>
					placeRowClicked(
						toggleShowFullName,
						props.placeActions.selectIndex,
						props.index
					)
				}
			>
				<strong data-testid={name}>{name}</strong>
				<AdditionalPlaceInfo showFullName={showFullName} location={location} placeActions={props.placeActions} index={props.index} place={props.place} 
									 distances={props.distances} 
									 nextPlace={props.places[(props.index+1)%props.places.length]}/>
			</td>
			<td style={{textAlign:'center', width:'25%'}}> {getCumulativeDistance(props)} </td>
			<td style={{textAlign:'center', width:'25%'}}> {"Distance to "} <b>{"Next Place"}</b> {": "} {props.distances[props.index]}</td>
			<RowArrow toggleShowFullName={toggleShowFullName} index={props.index}/>
		</tr>
	);
}

/*{props.places[(props.index+1)%props.places.length].defaultDisplayName}*/

function needToRecalculateDistances(lengths)
{
	return lengths.distancesLength != lengths.placesLength;
}

function AdditionalPlaceInfo(props) {
	return (
		<Collapse isOpen={props.showFullName}>
			{props.place.formatPlace().replace(`${props.place.defaultDisplayName}, `, '')}
			<br />
			{props.location}
			<br />
			<PlaceActions placeActions={props.placeActions} index={props.index} />
		</Collapse>
	);
}  

function placeRowClicked(toggleShowFullName, selectIndex, placeIndex) {
	toggleShowFullName();
	selectIndex(placeIndex);
}

function RowArrow(props) {
	return (
		<td>
			<BsChevronDown data-testid={`place-row-toggle-${props.index}`} onClick={props.toggleShowFullName}/>
		</td>
	);
}

async function getDistances(props)
{
	try
	{
		const serverUrl= getOriginalServerUrl();

		let placesData = props.places;
		let earthRadiusData = props.earthRadius;

		const distancesResponse = await sendAPIRequest({requestType: 'distances', earthRadius: earthRadiusData, places: placesData}, serverUrl);

		if(distancesResponse)
		{
			props.setDistances(distancesResponse.distances);
		}
		else
		{
			props.setDistances(["There was nothing here"]);
		}
	}
	catch {}
}

async function getTotalDistance(props)
{
	var total = 0;
	for(const distance of props.distances) {
		total += distance;
	}
	props.setTotalDistance(total);
}

function getCumulativeDistance(props)
{
	var cumulativeDistance = 0;

	if(props.index === 0){
		return cumulativeDistance;
	}
	else{
		let placeIndex = 0;
		while(placeIndex != props.index){
			cumulativeDistance += props.distances[placeIndex];
			++placeIndex;
		}
	}
	return cumulativeDistance;
}