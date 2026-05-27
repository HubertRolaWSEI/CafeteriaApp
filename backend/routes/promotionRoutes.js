const express = require("express");
const promotion = require("../data/promotion");

const router = express.Router();

router.get("/", (req, res) => {
  res.json(promotion);
});

module.exports = router;
