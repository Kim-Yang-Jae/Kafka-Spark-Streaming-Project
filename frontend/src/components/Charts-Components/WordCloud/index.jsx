import React, { useEffect, useState } from "react";
import WordCloud from "react-wordcloud";
import { useNavigate } from "react-router-dom";

const WordCloudContainer = ({ KeywordCount, dummy2 }) => {
  const navigate = useNavigate();

  const data = KeywordCount ? KeywordCount : dummy2;

  const options = {
    fontSizes: [10, 60],
    rotations: 2,
    rotationAngles: [0, 90],
    padding: 1,
    willReadFrequently: true,
  };

  const handleWordClick = (word) => {
    navigate(`/search/${word.text}`);
  };

  return (
    <div>
      <WordCloud
        style={{ fontFamily: "Jua, sans-serif" }}
        words={data}
        options={{
          ...options,
          fontFamily: "Do Hyeon, sans-serif",
        }}
        callbacks={{
          onWordClick: handleWordClick,
        }}
      />
    </div>
  );
};

export default WordCloudContainer;
