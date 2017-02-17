package cn.whaley.member.base.util;

public abstract class ExceptionRetryBase<T> {

	private int retryTimes = 1;

	private int maxRetryTimes = 3;

	private T lastResult = null;

	private boolean isPrintError = true;

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getMaxRetryTimes() {
		return maxRetryTimes;
	}

	public void setMaxRetryTimes(int maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
	}

	public boolean isPrintError() {
		return isPrintError;
	}

	public void setPrintError(boolean isPrintError) {
		this.isPrintError = isPrintError;
	}

	public T getLastResult() {
		return lastResult;
	}

	protected abstract T tryWork() throws Exception;

	protected void onExceedException() {

	}

	public boolean tryWorkUnderException() {
		if (retryTimes > maxRetryTimes) {
			onExceedException();
			return false;
		}

		try {
			lastResult = tryWork();
			return true;
		} catch (Exception e) {
			if (isPrintError) {
				e.printStackTrace();
			}

			retryTimes++;
			return tryWorkUnderException();
		}
	}

}
