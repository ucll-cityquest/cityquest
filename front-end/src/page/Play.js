import React from "react";
import PropTypes from "prop-types";
import Modal from "react-responsive-modal";
import classNames from "classnames";
import { Map, TileLayer, Marker, Popup, Circle } from "react-leaflet";
import { Redirect } from "react-router";
import { EndModal } from "../components/EndModal";
import { createApiUrl } from "../api";
import { createLocationStream } from "../geolocation";
import { getUserId, toRadian } from "../util";

class Play extends React.Component {
  static propTypes = {
    match: PropTypes.shape({
      params: PropTypes.shape({
        id: PropTypes.string.isRequired
      }).isRequired
    }).isRequired
  };

  state = {
    loading: true,
    toOverview: false,
    modalOpen: false,
    modal2Open: false,
    modalEndOpen: false,
    loadingStartLocation: true,
    showMap: false,
    startingLocation: [0, 0],
    location: [0, 0],
    game: {
      questions: [],
      coordinates: { lat: 0, lng: 0 }
    },
    activeQuestionsQueue: [],
    ignoredQuestions: [],
    activeQuestion: null,
    dev: {
      modN: 0,
      modE: 0
    },
    radius: 50
  };

  async componentDidMount() {
    this.startGeoLocation();
    this.setState({ loading: true });
    this.loadGame();
  }

  componentWillUnmount() {
    this.close();
  }

  renderMap() {
    const {
      startingLocation,
      location,
      radius,
      game: { questions }
    } = this.state;

    return (
      <Map
        center={startingLocation}
        zoom={15}
        style={{ width: 800, height: 400 }}
      >
        <TileLayer
          attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.osm.org/{z}/{x}/{y}.png"
        />
        <Marker position={location}>
          <Popup>
            <span>You are here!</span>
          </Popup>
        </Marker>
        {questions
          .filter(
            ({ selectedAnswer }) =>
              selectedAnswer !== undefined && selectedAnswer !== -1
          )
          .map(({ id, coordinates: { lat, lng }, question }) => (
            <React.Fragment key={id}>
              <Marker position={[lat, lng]}>
                <Popup>
                  <span>
                    Question:
                    {question}
                  </span>
                </Popup>
              </Marker>
              <Circle center={[lat, lng]} radius={radius}>
                <Popup>
                  <span>
                    Radius for question:
                    {radius}
                  </span>
                </Popup>
              </Circle>
            </React.Fragment>
          ))}
      </Map>
    );
  }

  onCloseModal() {
    const {
      activeQuestionsQueue,
      activeQuestion,
      ignoredQuestions
    } = this.state;
    this.setState(
      {
        modalOpen: false,
        activeQuestionsQueue: activeQuestionsQueue.filter(
          e => e.id !== activeQuestion.id
        ),
        ignoredQuestions: [...ignoredQuestions, activeQuestion],
        activeQuestion: null
      },
      () => this.updateActiveQuestions()
    );
  }

  onCloseModal2() {
    const {
      game: { questions }
    } = this.statel;
    this.setState({ modal2Open: false, activeQuestion: null }, () => {
      this.updateActiveQuestions();
      setTimeout(() => {
        const end = questions.every(
          el => el.selectedAnswer !== undefined && el.selectedAnswer !== -1
        );
        if (end) this.setState({ modalEndOpen: true });
      }, 2000);
    });
  }

  selectAnswer(index) {
    const { activeQuestion } = this.state;
    this.setState({
      activeQuestion: { ...activeQuestion, selectedAnswer: index }
    });
  }

  submitAnswer() {
    const { game, activeQuestion } = this.state;
    const questions = game.questions.map(question => {
      if (question.id === activeQuestion.id) {
        return {
          ...question,
          selectedAnswer: activeQuestion.selectedAnswer
        };
      }
      return question;
    });
    this.setState({ game: { ...game, questions }, modalOpen: false }, () =>
      setTimeout(() => this.setState({ modal2Open: true }), 200)
    );
  }

  renderActiveQuestion() {
    const {
      activeQuestion: {
        question,
        answers,
        selectedAnswer,
        correctAnswer,
        extraInformation
      },
      modalOpen,
      modal2Open
    } = this.state;

    return (
      <div>
        <Modal open={modalOpen} onClose={this.onCloseModal.bind(this)} center>
          <h2>{question}</h2>
          {answers.map((el, i) => {
            const isSelected = selectedAnswer === i;
            const classes = classNames("answer", {
              selectedAnswer: isSelected
            });

            return (
              <div style={{ display: "flex" }}>
                <button
                  type="button"
                  onClick={() => this.selectAnswer(i)}
                  className={classes}
                >
                  {el}
                </button>
              </div>
            );
          })}

          <button
            type="button"
            style={{ marginTop: 20 }}
            onClick={() => this.submitAnswer()}
          >
            Submit
          </button>
        </Modal>
        <Modal open={modal2Open} onClose={this.onCloseModal2.bind(this)} center>
          <h2>
            {selectedAnswer === correctAnswer
              ? "Congratulations, that was correct!"
              : "Bummer, that was wrong!"}
          </h2>
          <p>{extraInformation}</p>
        </Modal>
      </div>
    );
  }

  render() {
    const {
      loading,
      error,
      game,
      location,
      toOverview,
      showMap,
      dev,
      activeQuestion,
      modalEndOpen
    } = this.state;
    const {
      name,
      description,
      coordinates: { lat, lng }
    } = game;

    if (toOverview) {
      return <Redirect to="/" />;
    }
    if (loading) {
      return <h1>Loading game</h1>;
    }
    if (error) {
      return <h1 style={{ color: "red" }}>{error.toString()}</h1>;
    }
    return (
      <div style={{ marginLeft: 50 }}>
        <h1>{name}</h1>
        <h4>{description}</h4>
        <h4>{`${location} (${lat} ${lng})`}</h4>
        <p>
          Your current location is: (<span>{location[0]}</span>,{" "}
          <span>{location[1]}</span>)
        </p>
        {showMap && this.renderMap()}
        <button
          type="button"
          onClick={() => {
            this.setState({
              dev: { ...dev, modE: dev.modE + 0.001 },
              location: [location[0] + 0.001, location[1]]
            });
            this.updateActiveQuestions();
          }}
        >
          UP
        </button>
        <button
          type="button"
          onClick={() => {
            this.setState({
              dev: { ...dev, modE: dev.modE - 0.001 },
              location: [location[0] - 0.001, location[1]]
            });
            this.updateActiveQuestions();
          }}
        >
          DOWN
        </button>
        <button
          type="button"
          onClick={() => {
            this.setState({
              dev: { ...dev, modN: dev.modN + 0.001 },
              location: [location[0], location[1] + 0.001]
            });
            this.updateActiveQuestions();
          }}
        >
          RIGHT
        </button>
        <button
          type="button"
          onClick={() => {
            this.setState({
              dev: { ...dev, modN: dev.modN - 0.001 },
              location: [location[0], location[1] - 0.001]
            });
            this.updateActiveQuestions();
          }}
        >
          LEFT
        </button>
        {game !== null &&
          activeQuestion !== null &&
          this.renderActiveQuestion()}
        <EndModal
          isOpen={modalEndOpen}
          onClose={this.onCloseEndModal.bind(this)}
          onRate={this.rateGame.bind(this)}
        />
      </div>
    );
  }

  onCloseEndModal() {
    this.setState({ modalEndOpen: false, toOverview: true });
  }

  // TODO: refactor this
  updateActiveQuestions() {
    const { error, location, radius, modalOpen, game } = this.state;
    if (error) return;

    const isQuestionInRadius = el => {
      function getDistance(origin, destination) {
        // return distance in meters
        const lon1 = toRadian(origin[1]);

        const lat1 = toRadian(origin[0]);

        const lon2 = toRadian(destination[1]);

        const lat2 = toRadian(destination[0]);

        const deltaLat = lat2 - lat1;
        const deltaLon = lon2 - lon1;

        const a =
          Math.sin(deltaLat / 2) ** 2 +
          Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2) ** 2;

        const c = 2 * Math.asin(Math.sqrt(a));
        const EARTH_RADIUS = 6371;
        return c * EARTH_RADIUS * 1000;
      }

      const diff = getDistance(location, [
        el.coordinates.lat,
        el.coordinates.lng
      ]);
      return diff <= radius;
    };

    let activeQuestionsQueue = [...this.state.activeQuestionsQueue];
    let ignoredQuestions = [...this.state.ignoredQuestions];
    let activeQuestion =
      this.state.activeQuestion === null
        ? null
        : { ...this.state.activeQuestion };
    let setNew = false;

    const activeQuestionSetter = () => {
      // SETTING NEW ACTIVE QUESTION IF NECESSARY
      if (!activeQuestion && activeQuestionsQueue.length > 0) {
        for (let i = 0; i < activeQuestionsQueue.length; i += 1) {
          const item = activeQuestionsQueue[i];
          if (item.selectedAnswer === undefined || item.selectedAnswer === -1) {
            item.selectedAnswer = -1;
            this.setState(
              {
                activeQuestion,
                activeQuestionsQueue,
                ignoredQuestions,
                modalOpen
              },
              () =>
                this.setState({ activeQuestion: item, modalOpen: true }, () =>
                  this.checkEnd()
                )
            );
            return;
          }
        }
      }
    };

    game.questions.forEach(el => {
      const res1 = activeQuestionsQueue.some(question => el.id === question.id);
      const res2 = ignoredQuestions.some(question => el.id === question.id);

      if (!isQuestionInRadius(el)) {
        // REMOVING ITEM FROM ACTIVE QUESTIONS QUEUE
        if (res1 !== false)
          activeQuestionsQueue = activeQuestionsQueue.filter(
            (_, i) => res1 !== i
          );
        if (
          this.state.activeQuestion &&
          el.id === this.state.activeQuestion.id
        ) {
          activeQuestion = null;
          this.setState({
            modalOpen: false
          });
        }

        // REMOVING ITEM FROM IGNORED QUESTIONS
        if (res2 !== false)
          ignoredQuestions = ignoredQuestions.filter((_, i) => res2 !== i);
        return;
      }
      if (
        this.isQuestionInArray(el, activeQuestionsQueue) === false &&
        this.isQuestionInArray(el, ignoredQuestions) === false
      ) {
        activeQuestionsQueue = [...activeQuestionsQueue, el];
      }
      if (
        this.isQuestionInArray(el, activeQuestionsQueue) !== false &&
        activeQuestion === null &&
        (el.selectedAnswer === undefined || el.selectedAnswer === -1)
      ) {
        setNew = true;
      }
    });
    if (setNew) {
      activeQuestionSetter();
    } else {
      this.setState(
        {
          activeQuestion,
          activeQuestionsQueue,
          ignoredQuestions
        },
        () => this.checkEnd()
      );
    }
  }

  checkEnd() {
    const {
      game: { questions }
    } = this.state;

    const end = questions.every(
      el => el.selectedAnswer !== undefined && el.selectedAnswer !== -1
    );
    if (end) {
      // eslint-disable-next-line no-console
      console.log("at the end");
    }
  }

  startGeoLocation() {
    this.close = createLocationStream(location => {
      const otherProps = {};
      const startingProps = {};
      const { loadingStartLocation, dev } = this.state;

      if (loadingStartLocation) {
        startingProps.loadingStartLocation = false;
        startingProps.showMap = true;
        startingProps.startingLocation = location;
      }

      const { modN, modE } = dev;
      const locationWithDevOffset = [location[0] + modN, location[1] + modE];

      this.setState(
        { location: locationWithDevOffset, ...startingProps, ...otherProps },
        () => this.updateActiveQuestions()
      );
    });
  }

  async loadGame() {
    const {
      match: {
        params: { id }
      }
    } = this.props;

    try {
      const download = await fetch(createApiUrl(`games/${id}`));

      if (!download.ok) {
        this.setState({ error: "Game could not be found." });
        return;
      }
      const result = await download.json();
      this.setState({
        game: result
      });
    } catch (error) {
      this.setState({ error });
    } finally {
      this.setState({
        loading: false
      });
    }
  }

  async rateGame(rating) {
    const {
      game: { id }
    } = this.state;

    await fetch(createApiUrl(`games/${id}/rate`), {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        gameId: id,
        userId: getUserId(),
        rating
      })
    });
    this.onCloseEndModal();
  }
}

export default Play;
