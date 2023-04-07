import { useState, useEffect } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import MainNewsCard from "../../../Main-Components/MainNewsCard"
import SubArticles from '../Sub-articles';
import './subJournalist.scss';

// API
import {getSubscribe} from "../../../../apis/news"

const SubJournalist = ({userInfo}) => {
  
  const [selectedJournalist, setSelectedJournalist] = useState(null);

  const [subReporter, setsubReporter] = useState([])

  useEffect(() => {
    const fetchReporterList = async () => {
      try {
        const response = await getSubscribe();
        setsubReporter(response.lst);
      } catch (error) {
        console.error(error);
      }
    };
    fetchReporterList();
  }, []);

  const settings = {
    dots: true,
    arrows: true,
    infinite: true,
    speed: 1000,
    slidesToShow: subReporter.length < 5 ? subReporter.length : 5,
    slidesToScroll: 1,
    centerMode: true,
    focusOnSelect: true,
    responsive: [
      {
        breakpoint: 1400,
        settings: {
          slidesToShow: 3,
          centerMode: true,
        },
      },
      {
        breakpoint: 960,
        settings: {
          slidesToShow: 1,
          centerMode: true,
        },
      },
    ],
  };

  const handleJournalistClick = (reporter) => {
    setSelectedJournalist(reporter);
  };

  return (
    <div>
      <h2>"{userInfo.nickname}"님의 구독한 기자 : (총 {subReporter.length}명)</h2>
      <h4>- 구독한 기자들의 최신기사를 만나보세요</h4>
      <Slider {...settings}>
        {subReporter.map((reporter) => (
          <div key={reporter.reporterId} onClick={() => handleJournalistClick(reporter)}>
            {reporter.thumbnail ? (
              <div>
                <img
                  src={reporter.thumbnail}
                  loading="lazy"
                  alt=""
                  className="reporter-image"
                />
                <p>{reporter.press} {reporter.name}</p>
              </div>
            ) : (
              <div>
                <img
                  src="/images/thumbnail.png"
                  loading="lazy"
                  alt="image"
                  className="reporter-image"
                />
                <p>{reporter.press} {reporter.name}</p>
              </div>
            )}
          </div>
        ))}
      </Slider>
      <div className="journalist-info-container">
        {!selectedJournalist && (
          <div className="journalist-info">
            <div>
              <div>
              {subReporter.map((reporter) => (
                <SubArticles key={reporter.reporter_Id} name={reporter.name} />
              ))}
              </div>
            </div>
          </div>
        )}
        {selectedJournalist && (
          <div className="journalist-info">
            <SubArticles
              key={selectedJournalist.id}
              name={selectedJournalist.name}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default SubJournalist;
