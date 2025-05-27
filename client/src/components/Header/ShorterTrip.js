import React, { useState } from "react";
import { Button, Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";

export default function ShorterTrip(props){
    
}

function ShorterTripHeader(props){
    return (
        <ModalHeader className="ml-2" toggle={props.toggleShorterTrip}>
            Make Trip Shorter
        </ModalHeader>
    );
}

function ShorterTripBody(props){

}

function ShorterTripFooter(props){
    return(
        <ModalFooter>
            <CancelShorterTripButton
                clear={props.clear}
            />
        </ModalFooter>
    );
}

function ConfirmShorterTrip(props){

}

function CancelShorterTripButton(props){
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.clear();
                }
            }
            data-testid='close-shorter-trip'
        >
            Cancel 
        </Button>
    )    
}