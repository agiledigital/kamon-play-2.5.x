package kamon.play

import akka.util.ByteString
import kamon.trace.Tracer
import kamon.util.SameThreadExecutionContext
import play.api.libs.streams.Accumulator
import play.api.mvc.{ EssentialAction, EssentialFilter, RequestHeader, Result }

class KamonFilter extends EssentialFilter {

  override def apply(next: EssentialAction): EssentialAction = new EssentialAction {
    override def apply(requestHeader: RequestHeader): Accumulator[ByteString, Result] = {

      def onResult(result: Result): Result = {
        Tracer.currentContext.collect { ctx ⇒
          ctx.finish()
          PlayExtension.httpServerMetrics.recordResponse(ctx.name, result.header.status.toString)
          if (PlayExtension.includeTraceToken) result.withHeaders(PlayExtension.traceTokenHeaderName -> ctx.token)
          else result

        } getOrElse result
      }

      //override the current trace name
      Tracer.currentContext.rename(PlayExtension.generateTraceName(requestHeader))
      val nextAccumulator = next.apply(requestHeader)
      nextAccumulator.map(onResult)(SameThreadExecutionContext)
    }
  }

}
