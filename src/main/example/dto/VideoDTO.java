package main.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoDTO {
    @JsonProperty("video_id")
    private Long videoId;

    @JsonProperty("active_dates")
    private ActiveDatesDTO activeDates;

    @JsonProperty("duration_seconds")
    private Long durationSeconds;

    @JsonProperty("sell_price")
    private Double sellPrice;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public ActiveDatesDTO getActiveDates() {
        return activeDates;
    }

    public void setActiveDates(ActiveDatesDTO activeDates) {
        this.activeDates = activeDates;
    }

    public Long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "videoId=" + videoId +
                ", activeDates=" + activeDates +
                ", durationSeconds=" + durationSeconds +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
