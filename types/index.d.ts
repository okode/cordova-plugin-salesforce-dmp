export interface DMPPlugin {
  /**
   * @description      Initialize Dmp analytics.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  initialize(params: { apiKey: string }): Promise<string>;

  /**
   * @description      Send Requests Dmp analytics.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  sendRequests(params: any): Promise<string>;

  /**
   * @description      track page view.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  trackPage(params: { email: string, logged: boolean, path: string, pageType: string }): Promise<string>;

  /**
   * @description      Track event.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  fireEvent(params: { action: string, category: string, label: string }): Promise<string>;
}
