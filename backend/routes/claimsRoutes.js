const express = require("express");
const {
  clearClaims,
  createClaim,
  getClaims
} = require("../services/claimsStore");

const router = express.Router();

router.get("/", (req, res) => {
  res.json(getClaims());
});

router.post("/", (req, res) => {
  const { rewardId, rewardTitle, usedStamps } = req.body;

  if (!rewardId || !rewardTitle || !usedStamps) {
    return res.status(400).json({
      error: "rewardId, rewardTitle and usedStamps are required"
    });
  }

  const claim = createClaim({ rewardId, rewardTitle, usedStamps });
  res.status(201).json(claim);
});

router.delete("/", (req, res) => {
  clearClaims();
  res.status(204).send();
});

module.exports = router;
