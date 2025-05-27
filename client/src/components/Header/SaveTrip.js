import React, { useState } from "react";
import { Button, Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import {usePlaces} from "../../hooks/usePlaces"
import { FaCheck, FaTimes } from 'react-icons/fa';

export default function SaveTrip(props)
{
    const [showValidityIcon, setShowValidityIcon] = useState(false);
    const [disallowLoad, setDisallowLoad] = useState(true);
    
    function clear(){
    props.toggleSaveTrip();
    setShowValidityIcon(false);
    setDisallowLoad(true);

}
    return (
        <Modal isOpen={props.isOpen} toggle={clear}>
            <SaveTripHeader
                toggleSaveTrip={clear}
            />
            <SaveTripBody
            placeActions={props.placeActions}
            disallowLoad={props.disallowLoad}
            />
            
        </Modal>
);
}


function SaveAsXMLButton(props)
{
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.setSaveTrip([]);
                props.clear();
                }
            }
            data-testid='close-save'
        >
            XML
        </Button>
    )
}

function SaveAsJSONButton(props)
{
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.setSaveTrip([]);
                props.clear();
                }
            }
            data-testid='close-save'
        >
            JSON
        </Button>
    )
}

//CreateCancelButton&Functionality
function CancelSaveTripButton(props){
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.setSaveTrip([]);
                props.clear();
                }
            }
            data-testid='close-save'
        >
            Cancel 
        </Button>
    )
}



function SaveTripHeader(props) {
    return (
        <ModalHeader className=".html" toggle={props.toggleSaveTrip}>
            Save The Current Trip
        </ModalHeader>
    );
}

function SaveTripBody(props)
{

    return(  <div>
        

        <SaveAsXMLButton

        />
        
        <SaveAsJSONButton
        />
        <SaveTripFooter
            placeActions={props.placeActions} 
            disallowLoad={props.disallowLoad}
            clear={props.clear}

        /> </div>
);

}

function SaveTripFooter(props){
return (
    <ModalFooter>
    <CancelSaveTripButton
    clear={props.clear}
    />
    </ModalFooter>
);

}

