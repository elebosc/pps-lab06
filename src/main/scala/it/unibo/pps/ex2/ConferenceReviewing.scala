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
  
  override def loadReview(article: Int, scores: Map[Question, Int]): Unit = ???

  override def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, finalScore: Int): Unit = ???

  override def orderedScores(article: Int, question: Question): List[Int] = ???

  override def averageFinalScore(article: Int): Double = ???

  override def accepted(article: Int): Boolean = ???

  override def acceptedArticles(): Set[Int] = ???

  override def sortedAcceptedArticles(): List[(Int, Double)] = ???

  override def averageWeightedFinalScore(article: Int): Double = ???

  override def averageWeightedFinalScoreMap(): Map[Int, Double] = ???
