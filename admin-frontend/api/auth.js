export default ($axios) => ({
    signin(credentials) {
      return $axios.post("/admin/oauth/access_token", credentials)
    },
    validToken(token) {
      return $axios.get("/admin/detail", {token})
    }
  });
  