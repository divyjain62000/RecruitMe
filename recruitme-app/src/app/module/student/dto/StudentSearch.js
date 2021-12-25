import BaseFilter from '../../../dto/BaseFilter';

class StudentSearch extends BaseFilter {
    constructor() {
        this.enrollmentNumber = null;
        this.secondaryEmail = null;
        this.secondaryMobileNumber = null;
        this.isPlaceForFulltime = null;
        this.isPlaceForInternship = null;
        this.isBlacklisted = null;
        this.isAnyCriminalCase = null;
    }

    setEnrollmentNumber(enrollmentNumber) { this.enrollmentNumber = enrollmentNumber; }
    getEnrollmentNumber() { return this.enrollmentNumber; }
    setSecondaryEmail(secondaryEmail) { this.secondaryEmail = secondaryEmail; }
    getSecondaryEmail() { return this.secondaryEmail; }
    setSecondaryMobileNumber(secondaryMobileNumber) { this.secondaryMobileNumber = secondaryMobileNumber; }
    getSecondaryMobileNumber() { return this.secondaryMobileNumber; }
    setIsPlaceForFulltime(isPlaceForFulltime) { this.isPlaceForFulltime = isPlaceForFulltime; }
    getIsPlaceForFulltime() { return this.isPlaceForFulltime; }
    setIsPlaceForInternship(isPlaceForInternship) { this.isPlaceForInternship = isPlaceForInternship; }
    getIsPlaceForInternship() { return this.isPlaceForInternship; }
    setIsBlacklisted(isBlacklisted) { this.isBlacklisted = isBlacklisted; }
    getIsBlacklisted() { return this.isBlacklisted; }
    setIsAnyCriminalCase(isAnyCriminalCase) { this.isAnyCriminalCase = isAnyCriminalCase; }
    getIsAnyCriminalCase() { return this.isAnyCriminalCase; }

}