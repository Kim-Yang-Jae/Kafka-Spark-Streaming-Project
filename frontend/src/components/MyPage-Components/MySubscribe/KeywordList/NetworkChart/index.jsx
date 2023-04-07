import React, { useState } from 'react';
import NetworkGraph from 'react-network-graph';

const keywords = [
  { name: 'React', id: 'react' },
  { name: 'JavaScript', id: 'javascript' },
  { name: 'Node.js', id: 'nodejs' },
  { name: 'HTML', id: 'html' },
  { name: 'CSS', id: 'css' },
];

const links = [
  { source: 'react', target: 'javascript' },
  { source: 'javascript', target: 'nodejs' },
  { source: 'html', target: 'javascript' },
  { source: 'css', target: 'javascript' },
];

function Keyword({ word, onClick }) {
  return (
    <span
      style={{ padding: '5px', margin: '5px', backgroundColor: '#eee', cursor: 'pointer' }}
      onClick={() => onClick(word)}
    >
      {word}
    </span>
  );
}

function NetworkChart({ selectedKeyword }) {
  const nodes = keywords.map((keyword) => ({
    id: keyword.id,
    name: keyword.name,
    size: keyword.id === selectedKeyword ? 30 : 15,
    color: keyword.id === selectedKeyword ? '#f00' : '#000',
  }));

  const filteredLinks = links.filter(
    (link) => link.source === selectedKeyword || link.target === selectedKeyword
  );

  const links = filteredLinks.map((link) => ({
    source: link.source,
    target: link.target,
    color: '#999',
  }));

  return (
    <NetworkGraph
      data={{ nodes, links }}
      config={{
        nodeHighlightBehavior: true,
        height: 400,
        width: 800,
        directed: false,
        node: {
          color: '#000',
          size: 20,
          highlightStrokeColor: '#f00',
          fontSize: 14,
          highlightFontSize: 16,
        },
        link: {
          color: '#999',
          highlightColor: '#f00',
        },
      }}
    />
  );
}

const NetworkChart=()=> {
  const [selectedKeyword, setSelectedKeyword] = useState('');

  function handleKeywordClick(word) {
    setSelectedKeyword(word);
  }

  return (
    <div>
      <h1>Keywords</h1>
      <div>
        {keywords.map((word) => (
          <Keyword key={word.id} word={word.name} onClick={handleKeywordClick} />
        ))}
      </div>
      <hr />
      <h2>Selected Keyword: {selectedKeyword}</h2>
      {selectedKeyword && <NetworkChart selectedKeyword={selectedKeyword} />}
    </div>
  );
}

export default NetworkChart;
