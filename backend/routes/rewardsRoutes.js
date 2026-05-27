const express = require("express");
const rewards = require("../data/rewards");

const router = express.Router();

router.get("/", (req, res) => {
  res.json(rewards);
});

module.exports = router;
