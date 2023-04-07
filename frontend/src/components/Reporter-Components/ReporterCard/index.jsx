import React from "react";

const ReporterCard = ({ V }) => {
  return (
    <div className="reporter-card">
      <img src={V?.thumbnail} alt="" />
    </div>
  );
};

export default ReporterCard;
