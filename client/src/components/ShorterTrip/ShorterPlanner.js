import React from 'react';
import { Col, Container, Row } from 'reactstrap';
import Map from './Map/Map';

export default function ShorterPlanner(props) {
	return (
		<Container>
			<h2>Shorter Trip</h2>
			<Section>
				<Map
					{...props}
				/>
			</Section>
			<Section>
				<div>
					<div style={{float:'right'}} >
				<Button color="primary" > Apply</Button>
				&ensp;
				<Button color="secondary" > Cancel</Button>
				</div>
				</div>
			</Section>
		</Container>
	);
}

function Section(props) {
	return (
		<Row>
			<Col sm={12}>{props.children}</Col>
		</Row>
	);
}
