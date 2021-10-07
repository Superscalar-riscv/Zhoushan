package zhoushan

import chisel3._
import chisel3.util._

trait CacheBusParameters {
  val CacheBusUserWidth = 64 + ZhoushanConfig.FetchWidth * 2
}

object CacheBusParameters extends CacheBusParameters { }

trait CacheBusId extends Bundle with AxiParameters {
  val id = Output(UInt(AxiIdWidth.W))
}

trait CacheBusUser extends Bundle with CacheBusParameters {
  val user = Output(UInt(CacheBusUserWidth.W))
}

class CacheBusReq extends Bundle with CacheBusId {
  val addr = Output(UInt(AxiAddrWidth.W))
  val wdata = Output(UInt(AxiDataWidth.W))
  val wmask = Output(UInt((AxiDataWidth / 8).W))
  val wen = Output(Bool())
  val size = Output(UInt(2.W))
}

class CacheBusWithUserReq extends CacheBusReq with CacheBusUser { }

class CacheBusResp extends Bundle with CacheBusId {
  val rdata = Output(UInt(AxiDataWidth.W))
}

class CacheBusWithUserResp extends CacheBusResp with CacheBusUser { }

class CacheBusIO extends Bundle {
  val req = Decoupled(new CacheBusReq)
  val resp = Flipped(Decoupled(new CacheBusResp))
}

class CacheBusWithUserIO extends CacheBusIO {
  override val req = Decoupled(new CacheBusWithUserReq)
  override val resp = Flipped(Decoupled(new CacheBusWithUserResp))
}
