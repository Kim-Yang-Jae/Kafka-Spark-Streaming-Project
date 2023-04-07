const Dot = ({ num, scrollIndex }) => {
  const isCurrent = scrollIndex === num;
  const scale = isCurrent ? 2 : 1;

  return (
    <div
      style={{
        width: 10,
        height: 10,
        border: "1px solid #0066cc",
        opacity: isCurrent ? 0.6 : 0.2, // 활성화된 점만 불투명하게
        borderRadius: "50%",
        backgroundColor: isCurrent ? "#0066cc" : "transparent",
        transitionDuration: "0.5s",
        transitionProperty: "background-color, transform, opacity", // transitionProperty를 사용하여 점의 전체 스타일에 트랜지션을 적용
        transform: `scale(${scale})`,
        marginBottom: 10, // 각 점마다 margin-bottom 추가하여 간격을 벌림
      }}
    ></div>
  );
};

const Dots = ({ scrollIndex }) => {
  return (
    <div style={{ position: "fixed", top: "40%", right: 80 }}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
          alignItems: "center",
          width: 10,
          height: 150, // 전체 높이를 150px로 조정
        }}
      >
        <Dot num={1} scrollIndex={scrollIndex}></Dot>
        <Dot num={2} scrollIndex={scrollIndex}></Dot>
        <Dot num={3} scrollIndex={scrollIndex}></Dot>
        <Dot num={4} scrollIndex={scrollIndex}></Dot>
      </div>
    </div>
  );
};

export default Dots