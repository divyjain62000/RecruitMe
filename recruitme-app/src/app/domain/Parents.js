class Parents {
    constructor() {
        this.id = null;
        this.motherName = null;
        this.fatherName = null;
        this.mobileNumber = null;
    }
    setId(id) { this.id = id; }
    getId() { return this.id; }
    setMotherName(motherName) { this.motherName = motherName; }
    getMotherName() { return this.motherName; }
    setFatherName(fatherName) { this.fatherName = fatherName; }
    getFatherName() { return this.fatherName; }
    setMobileNumber(mobileNumber) { this.mobileNumber = mobileNumber; }
    getMobileNumber() { return this.mobileNumber; }

}

export default Parents;