name := "movement-analysis"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0"
)

play.Project.playScalaSettings ++ Seq(
  javaOptions in Test += "-Dconfig.file=conf/test.conf",
  testOptions in Test := Nil
)
