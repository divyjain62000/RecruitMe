class BaseFilter {
    constructor() {
        this.id = null;
        this.name = null;
        this.branchId = null;
        this.programId = null;
        this.primaryMobileNumber = null;
        this.primaryEmail = null;
        this.search = null;
    }

    setId(id) { this.id = id; }
    getId() { return this.id; }
    setName(name) { this.name = name; }
    getName() { return this.name; }
    setBranchId(branchId) { this.branchId = branchId; }
    getBranchId() { return this.branchId; }
    setProgramId(programId) { this.programId = programId; }
    getProgramId() { return this.programId; }
    setPrimaryMobileNumber(primaryMobileNumber) { this.primaryMobileNumber = primaryMobileNumber; }
    getPrimaryMobileNumber() { return this.primaryMobileNumber; }
    setPrimaryEmail(primaryEmail) { this.primaryEmail = primaryEmail; }
    getPrimaryEmail() { return this.primaryEmail; }
    setSearch(search) { this.search = search; }
    getSearch() { return this.search; }

}

export default BaseFilter;