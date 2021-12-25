class StudentRegisteredInDrive {
    constructor() {
        this.driveId = null;
        this.searchForUgStudents = null;
        this.searchForPgStudents = null;
        this.programId=null;
        this.branchId=null;
    }
    setDriveId(driveId) { this.driveId = driveId; }
    getDriveId() { return this.driveId; }
    setSearchForPgStudents(searchForPgStudents) { this.searchForPgStudents = searchForPgStudents; }
    getSearchForPgStudents() { return this.searchForPgStudents; }
    setSearchForUgStudents(searchForUgStudents) { this.searchForUgStudents = searchForUgStudents; }
    getSearchForUgStudents() { return this.searchForUgStudents; }
    setProgramId(ProgramId) { this.ProgramId = ProgramId; } 
    getProgramId() { return this.programId; } 
    setBranchId(branchId) { this.branchId = branchId; } 
    getBranchId() { return this.branchId; } 
}

export default StudentRegisteredInDrive;