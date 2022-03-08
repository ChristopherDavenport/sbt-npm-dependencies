val scalaJSVersion = sys.env.getOrElse("SCALAJS_VERSION", "1.8.0")

ThisBuild / crossScalaVersions := Seq("2.12.14")
ThisBuild / versionScheme := Some("early-semver")

ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(List("test"))
)

val catsV = "2.6.1"
val catsEffectV = "3.1.1"
val fs2V = "3.0.6"
val circeV = "0.14.1"


// Projects
lazy val `sbt-npm-dependencies` = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .aggregate(core)

lazy val core = project.in(file("core"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-npm-dependencies",
    scriptedBufferLog := false,
    scriptedLaunchOpts ++= Seq(
    "-Dplugin.version=" + version.value,
    s"-Dscalajs.version=$scalaJSVersion",
    "-Dsbt.execute.extrachecks=true" // Avoid any deadlocks.
    ),
    test := {
      (Test / test).value
      scripted.toTask("").value
    },
    addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion),
    // addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0"),
    libraryDependencies ++= Seq(
    //   "org.typelevel"               %% "cats-core"                  % catsV,
    //   "org.typelevel"               %% "cats-effect"                % catsEffectV,

    //   "co.fs2"                      %% "fs2-core"                   % fs2V,
    //   "co.fs2"                      %% "fs2-io"                     % fs2V,

      "io.circe"                    %% "circe-core"                 % circeV,
    //   // "io.circe"                    %% "circe-generic"              % circeV,
      "io.circe"                    %% "circe-parser"               % circeV,

    )
  )