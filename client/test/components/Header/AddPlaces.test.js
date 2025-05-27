import React from 'react';
import { beforeEach, describe, expect, jest, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { render, screen, waitFor } from '@testing-library/react';
import AddPlace from '../../../src/components/Header/AddPlace';
import {
	REVERSE_GEOCODE_RESPONSE,
	MOCK_PLACE_RESPONSE,
	VALID_FIND_RESPONSE,
	VALID_DAVE_FIND_RESPONSE,
	DAVE_MOCK_PLACE,
} from '../../sharedMocks';

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.085',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const props = {
		toggleAddPlace: jest.fn(),
		append: jest.fn(),
		appendBunch: jest.fn(),
		isOpen: true,
	};

	beforeEach(() => {

		fetch.resetMocks()

		render(
			<AddPlace
				append={props.append}
				appendBunch={props.appendBunch}
				isOpen={props.isOpen}
				toggleAddPlace={props.toggleAddPlace}
			/>
		);
	});

	test('base: validates input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});
	});

	test('base: handles invalid input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.paste(coordInput, '1');

		await waitFor(() => {
			expect(coordInput.value).toEqual('1');
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(true);
	});

	test('awohara: test the button getting enabled', async () => {
		fetch.resetMocks();
		fetch.mockResponse(VALID_FIND_RESPONSE);
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, '0CO1');

		await waitFor(() => {
			expect(coordInput.value).toEqual('0CO1');
		});

		const checkbox = screen.getByTestId('addPlaceBox-0')

		await waitFor(() => { expect(checkbox == undefined || checkbox == null).toEqual(false);});

		await waitFor(() => {
			user.click(checkbox);
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(false);
	});

	test('awohara: Adds selected places', async () => {
		fetch.resetMocks();
		fetch.mockResponse(VALID_DAVE_FIND_RESPONSE);
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, "dave");

		await waitFor(() => {
			expect(coordInput.value).toEqual("dave");
		});

		const [checkbox1, checkbox2, checkbox3] = 
			[screen.getByTestId('addPlaceBox-0'), screen.getByTestId('addPlaceBox-1'), screen.getByTestId('addPlaceBox-2')];

		await waitFor(() => {
			user.click(checkbox1);
			user.click(checkbox2);
			user.click(checkbox3);
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(false);
		await waitFor(() => {
			user.click(addButton);
		});

		expect(props.appendBunch).toHaveBeenCalledWith([DAVE_MOCK_PLACE,DAVE_MOCK_PLACE,DAVE_MOCK_PLACE]);
		expect(coordInput.value).toEqual('');
	});

	test('base: Adds place', async () => {
		fetch.resetMocks();
		fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(false);
		await waitFor(() => {
			user.click(addButton);
		});
		expect(props.append).toHaveBeenCalledWith(MOCK_PLACE_RESPONSE);
		expect(coordInput.value).toEqual('');
	});
});