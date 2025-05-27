import React from 'react';
import { describe, expect, test, jest, beforeEach } from "@jest/globals";
import SaveTrip from '../../../src/components/Header/SaveTrip';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';


describe('SaveTrip', () =>{
        const props = {
            toggleSaveTrip: jest.fn(),
            setDisallowLoad: jest.fn()
        }

        beforeEach(() => {
            render(<SaveTrip
            isOpen={true}
            toggleSaveTrip={props.toggleSaveTrip}
            setDisallowLoad={props.setDisallowLoad}

            />
            );
            
        })

test('Alaskan: Header loads open toggle of Save Trip Button', ()=>{
    screen.getByText("Save The Current Trip");
})


});