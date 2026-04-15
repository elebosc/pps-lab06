package it.unibo.pps.ex2

enum Question:
  case RELEVANCE
  case SIGNIFICANCE
  case CONFIDENCE
  case FINAL

trait ConferenceReviewing:
  def loadReview(article: Int, scores: Map[Question, Int]): Unit
  def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, finalScore: Int): Unit
  def orderedScores(article: Int, question: Question): List[Int]
  def averageFinalScore(article: Int): Double
  def accepted(article: Int): Boolean
  def acceptedArticles(): Set[Int]
  def sortedAcceptedArticles(): List[(Int, Double)]
  def averageWeightedFinalScore(article: Int): Double
  def averageWeightedFinalScoreMap(): Map[Int, Double]

class ConferenceReviewingImpl extends ConferenceReviewing:
  
  private final val AVG_FINAL_SCORE_ACCEPTANCE_THRESHOLD = 5.0

  private var reviews: List[(Int, Map[Question, Int])] = List()
  
  override def loadReview(article: Int, scores: Map[Question, Int]): Unit =
    if scores.size < Question.values.length then
      throw IllegalArgumentException()
    reviews = reviews :+ (article, scores)

  override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, finalScore: Int): Unit =
    val map: Map[Question, Int] = Map(
      Question.RELEVANCE -> relevance,
      Question.SIGNIFICANCE -> significance,
      Question.CONFIDENCE -> confidence,
      Question.FINAL -> finalScore
    )
    reviews = reviews :+ (article, map)

  private def scoresForArticle(article: Int): List[(Int, Map[Question, Int])] =
    reviews.filter((currArticle, _) => currArticle == article)

  private def questionScoresForArticle(article: Int, question: Question): List[Int] =
    scoresForArticle(article).map((_, scores) => scores(question))

  override def orderedScores(article: Int, question: Question): List[Int] =
    questionScoresForArticle(article, question).sorted

  private def average(list: List[Double]): Double = list.sum / list.length

  override def averageFinalScore(article: Int): Double =
    average(questionScoresForArticle(article, Question.FINAL).map(score => score.toDouble))
  
  private def isAverageFinalScoreHighEnough(article: Int): Boolean =
    averageFinalScore(article) > AVG_FINAL_SCORE_ACCEPTANCE_THRESHOLD

  private def isOneRelevanceScoreHighEnough(article: Int): Boolean =
    scoresForArticle(article).exists { case (_, scores) =>
      scores.exists { case (question, score) => question == Question.RELEVANCE && score >= 8 }
    }

  override def accepted(article: Int): Boolean =
    isAverageFinalScoreHighEnough(article) && isOneRelevanceScoreHighEnough(article)

  private def distinctArticles: List[Int] =
    reviews.map((article, _) => article).distinct

  override def acceptedArticles(): Set[Int] =
    distinctArticles.filter(article => accepted(article)).toSet

  override def sortedAcceptedArticles(): List[(Int, Double)] =
    given articleOrderingByAvgScoreDesc: Ordering[(Int, Double)] with
      def compare(e1: (Int, Double), e2: (Int, Double)): Int =
        val (_, avgScore1) = e1
        val (_, avgScore2) = e2
        avgScore1.compareTo(avgScore2)
    acceptedArticles()
      .map(article => (article, averageFinalScore(article)))
      .toList
      .sorted

  override def averageWeightedFinalScore(article: Int): Double =
    val weightedFinalScores = scoresForArticle(article)
      .map((_, scores) => scores(Question.FINAL) * scores(Question.CONFIDENCE) / 10.0)
    average(weightedFinalScores)

  override def averageWeightedFinalScoreMap(): Map[Int, Double] =
    distinctArticles
      .groupBy(identity)
      .collect { case (article, _) => (article, averageWeightedFinalScore(article)) }
