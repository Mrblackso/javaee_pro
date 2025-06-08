<template>
  <div class="video-container">
    <video autoplay muted loop class="responsive-video">
      video.playbackRate = 0.25;
      <source src="../assets/home.mp4" type="video/mp4">
    </video>
  </div>
</template>

<script>
export default {
  mounted() {
    const video = this.$refs.videoPlayer;
    if (video) {
      video.playbackRate = 0.25;  // 设置慢速
    }
  }
};
</script>
<style scoped>

.video-container {
  width: 100%;
  height: auto;
  overflow: hidden;
}

.responsive-video {
  width: 100%;
  height: auto;
  display: block;
}


</style>