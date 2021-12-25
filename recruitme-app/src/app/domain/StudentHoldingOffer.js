class StudentHoldingOffer {
    constructor() {
        this.id = null;
        this.studentId = null;
        this.company = null;
        this.jobType = null;
        this.salary = null;
    }
    setId(id) { this.id = id; }
    getId() { return this.id; }
    setStudentId(studentId) { this.studentId = studentId; }
    getStudentId() { return this.studentId; }
    setCompany(company) { this.company = company; }
    getCompany() { return this.company; }
    setJobType(jobType) { this.jobType = jobType; }
    getJobType() { return this.jobType; }
    setSalary(salary) { this.salary = salary; }
    getSalary() { return this.salary; }

}

export default StudentHoldingOffer;