const express = require("express");
const menu = require("../data/menu");

const router = express.Router();

router.get("/", (req, res) => {
  res.json(menu);
});

module.exports = router;
