class VideoMetaData {

    constructor() {
        this.id = null;
        this.videoExternalId = null;
        this.title = null;
        this.topic = null;
        this.uploadById = null;
        this.videoFile = null;
    }

    setId(id) { this.id = id; }
    getId() { return this.id; }
    setVideoExternalId(videoExternalId) { this.videoExternalId = videoExternalId; }
    getVideoExternalId() { return this.videoExternalId; }
    setTitle(title) { this.title = title; }
    getTitle() { return this.title; }
    setTopic(topic) { this.topic = topic; }
    getTopic() { return this.topic; }
    setUploadById(uploadById) { this.uploadById = uploadById; }
    getUploadById() { return this.uploadById; }
    setVideoFile(videoFile) { this.videoFile = videoFile; }
    getVideoFile() { return this.videoFile; }

}

export default VideoMetaData;