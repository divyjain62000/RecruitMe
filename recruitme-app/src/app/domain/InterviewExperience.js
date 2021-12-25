class InterviewExperience {
    constructor() {
        this.id = null;
        this.studentId = null;
        this.company = null;
        this.passoutYear = null;
        this.salary = null;
        this.isFullTimeVacancy = null;
        this.isInternshipVacancy = null;
        this.experience = null;
        this.ugStudent = null;
        this.pgStudent = null;
    }

    setId(id) { this.id = id; }
    getId() { return this.id; }
    setStudentId(studentId) { this.studentId = studentId; }
    getStudentId() { return this.studentId; }
    setCompany(company) { this.company = company; }
    getCompany() { return this.company; }
    setPassoutYear(passoutYear) { this.passoutYear = passoutYear; }
    getPassoutYear() { return this.passoutYear; }
    setSalary(salary) { this.salary = salary; }
    getSalary() { return this.salary; }
    setIsFullTimeVacancy(isFullTimeVacancy) { this.isFullTimeVacancy = isFullTimeVacancy; }
    getIsFullTimeVacancy() { return this.isFullTimeVacancy; }
    setIsInternshipVacancy(isInternshipVacancy) { this.isInternshipVacancy = isInternshipVacancy; }
    getIsInternshipVacancy() { return this.isInternshipVacancy; }
    setExperience(experience) { this.experience = experience; }
    getExperience() { return this.experience; }
    setUgStudent(ugStudent) { this.ugStudent = ugStudent; }
    getUgStudent() { return this.ugStudent; }
    setPgStudent(pgStudent) { this.pgStudent = pgStudent; }
    getPgStudent() { return this.pgStudent; }
}

export default InterviewExperience;