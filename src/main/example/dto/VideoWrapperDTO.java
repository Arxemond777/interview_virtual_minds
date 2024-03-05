package main.example.dto;

import java.util.*;

public class VideoWrapperDTO {
    private List<VideoDTO> videos;

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "VideoWrapperDTO{" +
                "videos=" + videos +
                '}';
    }
}
