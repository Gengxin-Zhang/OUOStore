export default ($axios) => ({
    getSystemInfo(credentials) {
      return $axios.get("/system")
    },
  });
  