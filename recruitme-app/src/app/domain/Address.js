class Address {
    constructor() {
        this.id = null;
        this.addressLine1 = null;
        this.addressLine2 = null;
        this.countryId = null;
        this.stateId = null;
        this.cityId = null;
        this.pinCode=null //newly added
    }
    setId(id) { this.id = id; }
    getId() { return this.id; }
    setAddressLine1(addressLine1) { this.addressLine1 = addressLine1; }
    getAddressLine1() { return this.addressLine1; }
    setAddressLine2(addressLine2) { this.addressLine2 = addressLine2; }
    getAddressLine2() { return this.addressLine2; }
    setCountryId(countryId) { this.countryId = countryId; }
    getCountryId() { return this.countryId; }
    setStateId(stateId) { this.stateId = stateId; }
    getStateId() { return this.stateId; }
    setCityId(cityId) { this.cityId = cityId; }
    getCityId() { return this.cityId; }
    setPinCode(pinCode) { this.pinCode=pinCode; }
    getPinCode() { return this.pinCode; }

}

export default Address;