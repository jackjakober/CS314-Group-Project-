import '@testing-library/jest-dom';
import {act, renderHook} from '@testing-library/react-hooks';
import { beforeEach, describe, expect, test } from '@jest/globals';
import { MOCK_TOUR_RESPONSE_1, MOCK_PLACES_FOR_TOUR_1 } from '../sharedMocks';
import { LOG } from '../../src/utils/constants';
import { useTour } from '../../src/hooks/useTour';

describe('useTour', () => {

    let hook;

    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
        const { result } = renderHook(() => useTour(MOCK_PLACES_FOR_TOUR_1, 3959,1));
        hook = result;
    });

    test('awohara: base test', async () => {
        fetch.mockResponse(MOCK_TOUR_RESPONSE_1);
        expect(hook.current.shorterTrip).toEqual([]);

        await act(async () => {
            hook.current.tourActions.getShorterTrip();
        });

        expect(hook.current.places).toEqual(MOCK_TOUR_RESPONSE_1.places);
    });

});