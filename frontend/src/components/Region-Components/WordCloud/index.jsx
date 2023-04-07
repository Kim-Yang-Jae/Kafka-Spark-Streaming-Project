import { useState } from "react";
import ReactWordcloud from "react-wordcloud";
import { select } from "d3-selection";

import words from "./words.json";
import newsData from "../KeywordNews/newsData.json";


const callbacks = {
  getWordTooltip: (word) =>
    word.value > 50
      ? `"${word.text}"가 언급된 기사는 ${word.value} 건 입니다.`
      : `"${word.text}"가 언급된 기사는 ${word.value} 건 입니다.`,
};

const options = {
  rotations: 2,
  rotationAngles: [0],
  fontSizes: [10, 50],
  fontFamily:'Jua, sans-serif',
  tooltipOptions: {
    placement: 'bottom',
    style: {
      fontWeight: 'bold',
      fontFamily: 'Jua, sans-serif',
    },
  },
}

function WordCloud(props) {
  const [selectedWord, setSelectedWord] = useState(null);
  const [key, setKey] = useState(0);

  const relatedNews = newsData.filter((news) => {
    if (news.region === props.region) {
      return news.keyword;
    } else {
      return false;
    }
  });

  const allKeywords = relatedNews.flatMap((news) => news.keyword);
  const uniqueKeywords = [...new Set(allKeywords)];
  const matchedWords = words.filter((word) => uniqueKeywords.includes(word.text));

  function handleWordSelect(word) {
    setSelectedWord(word);
    props.onWordSelect(word); // 선택한 단어를 부모 컴포넌트에 전달
    setKey((prevKey) => prevKey + 1); // key 값을 변경하여 워드클라우드를 새로고침
  }

  function handleWordMouseOver(word, event) {
    const element = event.target;
    const text = select(element);
    text
      .transition()
      .attr("background", "white")
      .attr("font-size", "300%")
      .attr("text-decoration", "underline");
  }

  function handleWordMouseOut(word, event) {
    const element = event.target;
    const text = select(element);
    text
      .transition()
      .attr("background", "transparent")
      .attr("font-size", "100%")
      .attr("text-decoration", "none");
  }

  return (
    <div>
      <ReactWordcloud
        callbacks={{
          ...callbacks,
          onWordClick: handleWordSelect,
          onWordMouseOver: handleWordMouseOver,
          onWordMouseOut: handleWordMouseOut,
        }}
        words={matchedWords}
        options={options}
        key={key}
      />
      {/* {selectedWord && (
        <p>
          선택한 단어 : "{selectedWord.text}", 빈도수 : {selectedWord.value}
        </p>
      )} */}

    </div>
  );
}

export default WordCloud;
