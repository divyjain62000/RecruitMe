export const searchStudentById=(studentList,id)=>{
    let low=0;
    let high=studentList.size();
    while(low<=high) {
        let mid=(low+high)/2;
        if(studentList[mid].id==id) {
            return studentList[mid];
        }else if(studentList[mid].id>id) {
            high=mid-1;
        }else {
            low=mid+1;
        }
    }
    return null;
}