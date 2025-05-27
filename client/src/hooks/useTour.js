import { useState } from 'react';
import { reverseGeocode } from '../utils/reverseGeocode';
import { LOG } from '../utils/constants';
import { getOriginalServerUrl, sendAPIRequest } from '../utils/restfulAPI.js';

export function useTour(places, earthRadius, responseTime) {
    const [shorterTrip, setShorterTrip] = useState([]);

    const context = { places, responseTime, setShorterTrip, earthRadius};

    const tourActions = {
        getShorterTrip: async () => getShorterTrip(context)
    };

    return {shorterTrip, tourActions};
}

async function getShorterTrip(props)
{
		const serverUrl= getOriginalServerUrl();

		const TourResponse = await sendAPIRequest({requestType: 'tour', earthRadius: props.earthRadius, response:props.responseTime, places: props.places}, serverUrl);

        LOG.error(TourResponse);

		if(TourResponse)
		{
			props.setShorterTrip(TourResponse.places);
		}
}