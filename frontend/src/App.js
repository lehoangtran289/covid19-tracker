import React from "react";
import DataService from "./services/DataService";
import "./App.css";
import "./App2.css";
import { MDBDataTable } from "mdbreact";

class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			list: [],
			inputValue: "",
			isLoad: false
		};
	}

	componentDidMount() {
		DataService.getlist().then(res => {
			this.setState({
				list: res.data,
				isLoad: !this.state.isLoad
			});
		});
	}

	render() {
		const data = {
			columns: [
				{
					label: "Country/Region",
					field: "country",
					sort: "asc",
					width: 150
				},
				{
					label: "Province/State",
					field: "state",
					width: 270
				},
				{
					label: "Total Cases",
					field: "totalCases",
					sort: "asc",
					width: 100
				},
				{
					label: "Changes(+-)",
					field: "diff",
					sort: "asc",
					width: 200
				},
				{
					label: "Total Recovers",
					field: "totalRecovered",
					sort: "asc",
					width: 100
				},
				{
					label: "Total Deaths",
					field: "totalDeaths",
					sort: "asc",
					width: 150
				}
			],
			rows: this.state.list
		};
		if (this.state.isLoad) {
			const totalCases = this.state.list.reduce((a, { totalCases }) => a + totalCases, 0);
			const totalDeaths = this.state.list.reduce((a, { totalDeaths }) => a + totalDeaths, 0);
			const totalRecovered = this.state.list.reduce((a, { totalRecovered }) => a + totalRecovered, 0);
			const totalDiff = this.state.list.reduce((a, { diff }) => a + diff, 0);

			const value = this.state.inputValue;

			return (
				<div className="App container mt-4">
					<div className="container" align="center">
						<h1>COVID19 LATEST REPORT</h1>
						<div className="font-italic mt-3">
							This application lists the latest reported COVID19 across the globe. Reports are updated automatically once a day
						</div>
					</div>

					<hr className="mt-4" />

					<div className="jumbotron jumbotron-fluid mt-4">
						<div className="container">
							<div className="row">
								<div className="col-3 text-center">
									<h1 className="display-4">{totalCases}</h1>
									<p className="lead">Total reported cases</p>
								</div>
								<div className="col-3 text-center">
									<h1 className="display-4">{totalDiff}</h1>
									<p className="lead">
										New cases in {`${new Date().getDate()}/${new Date().getUTCMonth() + 1}/${new Date().getFullYear()}`}
									</p>
								</div>
								<div className="col-3 text-center">
									<h1 className="display-4">{totalDeaths}</h1>
									<p className="lead">Total Deaths</p>
								</div>
								<div className="col-3 text-center">
									<h1 className="display-4">{totalRecovered}</h1>
									<p className="lead">Total Recovered Cases</p>
								</div>
							</div>
						</div>
					</div>

					<div className="container mt-4">
						<h5>Search by a country/region or province/state</h5>
						<div className="form-group">
							<input
								className="col-5 form-control mt-3"
								type="text"
								placeholder="insert a value"
								onChange={event => {
									this.setState({
										inputValue: event.target.value
									});
									console.log(event.target.value);
								}}
							/>
						</div>
					</div>

					<div className="container">
						<table className="table table-striped mt-4">
							<thead>
								<tr>
                  <th>#</th>
									<th>Country/Region</th>
									<th>Province/State</th>
									<th>Total Cases</th>
									<th>Changes(+-)</th>
									<th>Total Recovered</th>
									<th>Total Deaths</th>
								</tr>
							</thead>
							<tbody>
								{this.state.list.map((item, index) => {
                  if(item.country.includes(value) || item.state.includes(value)) {
										return (
											<tr key={index}>
                        <td>{index + 1}</td>
												<td>{item.country}</td>
												<td>{item.state}</td>
												<td>{item.totalCases}</td>
												<td>{item.diff}</td>
												<td>{item.totalRecovered}</td>
												<td>{item.totalDeaths}</td>
											</tr>
										);
                  }
								})}
							</tbody>
						</table>
					</div>
					{/* <MDBDataTable striped bordered data={data} /> */}
				</div>
			);
		} else {
			return (
				<div className="container mt-5 text-center">
					<h1>Loading...</h1>
				</div>
			);
		}
	}
}

export default App;
