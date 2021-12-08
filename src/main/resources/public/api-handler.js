const GET_COUNTRY_BY_NAME_URL = 'http://localhost:8080/countries/';
const GET_ALL_COUNTRIES_URL = 'http://localhost:8080/countries';
const SECRET_KEY = "f25dc802ba3fd7e32797d65321396a0b";
// Button click handler
const onSearchByNameButtonClick = () => {
  const countryName = document.querySelector('#countryName').value.trim();
  if (!countryName) {
          return handleEmptyCheck();
  }
  // Calling the API and passing the result with the displayResult as a
	// callback function
   return searchByName(countryName, displayResult);
};
const onSearchAllButtonClick = () => {
	 return searchAll(displayAllResult);
};
const searchByName = (name, callback) => {
  let url = new URL(name,GET_COUNTRY_BY_NAME_URL); 
  axios.get(url, { headers: { 'access_key': SECRET_KEY } })
          .then(response => {
            const data = response.data;
             callback(data)
          })
          .catch(error => {
          console.error(error);
          const resultBlockElement = document.querySelector('#country-result');
          resultBlockElement.classList.remove('invisible');
          document.querySelector('#country').innerText = `Country with name '${name}' not found. ${error}`;
          })
};
const searchAll = (callback) => {
  let url = new URL(GET_ALL_COUNTRIES_URL); 
  axios.get(url,{ headers: { "access_key": SECRET_KEY } })
          .then(response => {
            const data = response.data;
             callback(data)
          })
          .catch(error => {
            console.error(error);
            const resultBlockElement = document.querySelector('#all-country-result');
            resultBlockElement.classList.remove('invisible');
            document.querySelector('#all-country').innerText = ` Error occured : ${error}`;
            })
};
const displayResult = result => {
	const resultBlockElement = document.querySelector('#country-result');
	resultBlockElement.classList.remove('invisible');
	document.querySelector('#country').innerHTML = 
    ' <tr>'+
      '<th>Name</th>'+
      '<th>Country Code</th>'+
      '<th>Capital</th>'+
      '<th>Region</th>'+
      '<th>Alpha3Code</th>'+
    '</tr>' +
    '<tr>'+
    '<td>'+result.name+'</td>'+
    '<td>'+result.country_code+'</td>'+
    '<td>'+result.capital+'</td>'+
    '<td>'+result.region+'</td>'+
    '<td>'+result.alpha3Code+'</td>'+
  '</tr>';
 };
 const displayAllResult = result => {
  	const resultBlockElement = document.querySelector('#all-country-result');
	resultBlockElement.classList.remove('invisible');
    document.querySelector('#all-country').innerHTML= 
    '<tr>'+
    '<th>Name</th>'+
    '<th>Country Code</th>'+
    '</tr>'+appendResult(result.countries);
};
 
const appendResult = countries =>{
	let returnString = '';
	countries.forEach(country => {
		returnString += '<tr>'+
	        '<td>'+country.name+'</td>'+
	        '<td>'+country.country_code+'</td>'+
	        '</tr>'
	    }
	);
	return returnString;
}
const handleEmptyCheck = () => {
  const resultBlockElement = document.querySelector('#country-result');
  resultBlockElement.classList.add('invisible');
  return alert('Name you entered is empty');
};