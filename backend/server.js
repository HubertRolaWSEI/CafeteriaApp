const express = require("express");
const cors = require("cors");

const claimsRoutes = require("./routes/claimsRoutes");
const menuRoutes = require("./routes/menuRoutes");
const promotionRoutes = require("./routes/promotionRoutes");
const rewardsRoutes = require("./routes/rewardsRoutes");

const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

app.get("/", (req, res) => {
  res.json({
    name: "Cafeteria backend",
    endpoints: [
      "/api/menu",
      "/api/rewards",
      "/api/promotion",
      "/api/claims"
    ]
  });
});

app.use("/api/menu", menuRoutes);
app.use("/api/rewards", rewardsRoutes);
app.use("/api/promotion", promotionRoutes);
app.use("/api/claims", claimsRoutes);

app.listen(port, () => {
  console.log(`Cafeteria backend running on http://localhost:${port}`);
});
