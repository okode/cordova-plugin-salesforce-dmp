export interface DMPPlugin {


// TODO - Plugin Errors
// readonly DMP__REQUEST__ACCOUNTS_NOT_FOUND;

  /**
   * @description      Initialize Dmp analytics.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  initialize(params): Promise <string> ;

  /**
   * @description      Send Requests Dmp analytics.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  sendRequests(params): Promise <string> ;
  /**
   * @description      track page view.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  trackPage(params): Promise <string> ;

  /**
   * @description      Track event.
   *                   On Android, if several are found, it will open a modal for user selection.
   * @returns
   *                   text: if succesfully retrieved.
   * @errors
   *                   DMP__REQUEST__ACCOUNTS_NOT_FOUND: if there is no text saved
   */
  fireEvent(params): Promise <string> ;

}

