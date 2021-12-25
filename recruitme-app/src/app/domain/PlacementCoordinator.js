class PlacementCoordinator {
    constructor() {

        this.id = null;
        this.name = "";
        this.primaryEmail = "";
        this.primaryMobileNumber = "";
        this.passwordKey = "";
        this.password = "";
        this.gender = null;
        this.programId = null;
        this.branchId = null;

    }

    setId(id) { this.id = id; }
    getId() { return this.id; }
    setName(name) { this.name = name; }
    getName() { return this.name; }
    setPrimaryEmail(primaryEmail) { this.primaryEmail = primaryEmail; }
    getPrimaryEmail() { return this.primaryEmail; }
    setPrimaryMobileNumber(primaryMobileNumber) { this.primaryMobileNumber = primaryMobileNumber; }
    getPrimaryMobileNumber() { return this.primaryMobileNumber; }
    setPasswordKey(passwordKey) { this.passwordKey = passwordKey; }
    getPasswordKey() { return this.passwordKey; }
    setPassword(password) { this.password = password; }
    getPassword() { return this.password; }
    setGender(gender) { this.gender = gender; }
    getGender() { return this.gender; }
    setProgramId(programId) { this.programId = programId; }
    getProgramId() { return this.programId; }
    setBranchId(branchId) { this.branchId = branchId; }
    getBranchId() { return this.branchId; }



}

export default PlacementCoordinator;